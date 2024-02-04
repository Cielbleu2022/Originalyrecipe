package org.recipe.originalyrecipe.services;

import org.recipe.originalyrecipe.mappers.UtilisateurMapper;
import org.recipe.originalyrecipe.models.dto.UtilisateurDTO;
import org.recipe.originalyrecipe.models.entity.Role;
import org.recipe.originalyrecipe.models.entity.Utilisateur;
import org.recipe.originalyrecipe.models.form.UtilisateurForm;
import org.recipe.originalyrecipe.models.updateForm.UtilisateurUpdateForm;
import org.recipe.originalyrecipe.repository.RoleRepository;
import org.recipe.originalyrecipe.repository.UtilisateurRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class UtilisateurService implements BaseService<UtilisateurDTO, Long, Utilisateur, UtilisateurForm, UtilisateurUpdateForm> {

    private final UtilisateurRepository utilisateurRepository;
    private final UtilisateurMapper utilisateurMapper;
    private final RoleRepository roleRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    public static final Logger logger = LoggerFactory.getLogger(UtilisateurService.class);
    // Ajoutez PasswordEncoder dans le constructeur
    public UtilisateurService(
            UtilisateurRepository utilisateurRepository,
            UtilisateurMapper utilisateurMapper,
            RoleRepository roleRepository,
            RoleService roleService,
            PasswordEncoder passwordEncoder) {
        this.utilisateurRepository = utilisateurRepository;
        this.utilisateurMapper = utilisateurMapper;
        this.roleRepository = roleRepository;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;  // Initialisez PasswordEncoder
    }

    @Override
    public List<UtilisateurDTO> getAll() {
        return utilisateurRepository.findAll()
                .stream()
                .map(utilisateurMapper::entityToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UtilisateurDTO findOne(Long aLong) {
        return utilisateurRepository.findById(aLong)
                .map(utilisateurMapper::entityToDTO)
                .orElseThrow(() -> new NoSuchElementException("Utilisateur non trouvé avec l'identifiant, essayer un autre identifiant : " + aLong));
    }

    @Override
    public UtilisateurDTO add(UtilisateurForm toAdd) {
        Utilisateur utilisateur = utilisateurMapper.formToEntity(toAdd);

        // Récupérer le rôle à partir du service, si le roleName est spécifié, sinon attribuer le rôle par défaut "client"
        Role role = (toAdd.getRoleName() != null && !toAdd.getRoleName().isEmpty())
                ? roleService.createRole(toAdd.getRoleName())
                : roleService.createRole("client");

        // Si le rôle n'est ni "client", ni "admin", ni "partenaire", attribuer le rôle par défaut "client"
        if (!isValidRole(role.getName())) {
            role = roleService.createRole("client");
        }

        utilisateur.setRole(role);

        // Encoder le mot de passe avec BCrypt avant de le stocker
        String motDePasseEncode = passwordEncoder.encode(toAdd.getMotDePasse());
        utilisateur.setMotDePasse(motDePasseEncode);

        // Enregistrez le nouvel utilisateur
        Utilisateur savedUtilisateur = utilisateurRepository.save(utilisateur);

        // Recherchez l'utilisateur par e-mail après l'enregistrement
        UtilisateurDTO utilisateurDTO = findByMail(savedUtilisateur.getMail());

        return utilisateurDTO;
    }

    private boolean isValidRole(String name) {
        if (name == null || name.trim().isEmpty()) {
            return false; // Le nom de rôle est invalide s'il est null ou une chaîne vide
        }

        // Liste des rôles autorisés
        String[] allowedRoles = {"client", "admin", "partenaire"};

        // Vérifiez si le rôle spécifié est autorisé
        for (String allowedRole : allowedRoles) {
            if (allowedRole.equalsIgnoreCase(name)) {
                return true;
            }
        }

        // Aucun rôle autorisé trouvé
        return false;
    }

    @Override
    public UtilisateurDTO update(Long aLong, UtilisateurUpdateForm utilisateurUpdateForm) {
        Utilisateur utilisateur = utilisateurRepository.findById(aLong).orElseThrow();
        utilisateur.setMail(utilisateurUpdateForm.getMail());
        utilisateur.setPays(utilisateurUpdateForm.getPays());

        // Mettre à jour le rôle si le nom du rôle change
        if (!utilisateur.getRole().getName().equals(utilisateurUpdateForm.getRoleName())) {
            Role newRole = roleRepository.findByName(utilisateurUpdateForm.getRoleName());
            utilisateur.setRole(newRole);
        }

        return utilisateurMapper.entityToDTO(utilisateurRepository.save(utilisateur));
    }

    @Override
    public UtilisateurDTO remove(Long aLong) {
        Utilisateur utilisateur = utilisateurRepository.findById(aLong).orElseThrow();
        utilisateurRepository.delete(utilisateur);
        return utilisateurMapper.entityToDTO(utilisateur);
    }

    @Override
    public List<UtilisateurDTO> searchByName(String nom) {
        return utilisateurRepository.findByNomContainingIgnoreCase(nom)
                .stream()
                .map(utilisateurMapper::entityToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UtilisateurDTO findByMail(String mail) {
        Utilisateur utilisateur = utilisateurRepository.findByMail(mail);
        if (utilisateur == null) {
            throw new NoSuchElementException("Utilisateur non trouvé avec l'adresse e-mail: " + mail);
        }
        return utilisateurMapper.entityToDTO(utilisateur);
    }

    @Override
    public UtilisateurDTO login(String email, String password) throws AuthenticationException {
        Utilisateur utilisateur = utilisateurRepository.findByMail(email);
        if (utilisateur == null || !passwordEncoder.matches(password, utilisateur.getMotDePasse())) {
            // Ajouter des logs ici
            logger.warn("Login failed for email: {}", email);
            throw new AuthenticationException("Échec de la connexion. Vérifiez vos identifiants.");
        }
        logger.info("Login successful for email: {}", email);
        return utilisateurMapper.entityToDTO(utilisateur);
    }
    @Override
    public void logout() {
        // Ajoutez ici la logique de déconnexion
        // Par exemple, invalidez la session, effectuez d'autres opérations nécessaires, etc.

        // Effacer le contexte de sécurité pour déconnecter l'utilisateur
        SecurityContextHolder.clearContext();
    }


}
