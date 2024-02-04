package org.recipe.originalyrecipe.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.recipe.originalyrecipe.models.dto.UtilisateurDTO;
import org.recipe.originalyrecipe.models.form.UtilisateurForm;
import org.recipe.originalyrecipe.models.updateForm.UtilisateurUpdateForm;
import org.recipe.originalyrecipe.services.UtilisateurService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import static org.recipe.originalyrecipe.services.UtilisateurService.logger;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("utilisateur")
@Tag(name = "Utilisateur Controller", description = "Endpoints pour gérer les utilisateurs")
public class UtilisateurController {
    private final UtilisateurService utilisateurService;
    /**
     * Constructeur du contrôleur avec injection de dépendance.
     *
     * @param utilisateurService Le service d'utilisateur à injecter.
     */
    public UtilisateurController(UtilisateurService utilisateurService) {
        this.utilisateurService = utilisateurService;
    }

    /**
     * Récupère tous les utilisateurs.
     *
     * @return Une ResponseEntity contenant la liste de tous les utilisateurs.
     */
    @GetMapping(path = {"","/","/all"})
    @Operation(summary = "Récupère tous les utilisateurs")
    @ApiResponse(responseCode = "200", description = "Liste des utilisateurs récupérée avec succès")

    public ResponseEntity<List <UtilisateurDTO>>getAll(){
        return ResponseEntity
                .ok(utilisateurService.getAll());
    }/**
     * Recherche un utilisateur.
     *
     * @return Une ResponseEntity contenant un utilisateur.
     */


    @GetMapping(path = {"/searchUtilisateur"},params = {"idUtilisateur"})
    @Operation(summary = "Recherche d'un utilisateur par ID")
    //@Parameter(description = "ID de l'utilisateur à rechercher", required = true)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Utilisateur trouvé", content = @Content(schema = @Schema(implementation = UtilisateurDTO.class))),
           @ApiResponse(responseCode = "404", description = "Utilisateur non trouvé")
    })
    public ResponseEntity<UtilisateurDTO> findOne(@RequestParam Long idUtilisateur){
        return ResponseEntity
                .ok(utilisateurService.findOne(idUtilisateur));
    }


    /**
     * Ajoute un nouvel utilisateur.
     *
     * @param utilisateurForm Les informations de l'utilisateur à ajouter.
     * @param headers          Les en-têtes de la requête.
     * @return Le DTO de l'utilisateur ajouté.
     */
    @PostMapping(path = {"", "/", "/add"})
    @Operation(summary = "Ajoute un nouvel utilisateur")
    @ApiResponse(responseCode = "201", description = "Utilisateur ajouté avec succès")
    @ApiResponse(responseCode = "400", description = "Requête invalide")

    public UtilisateurDTO insert(@Valid @RequestBody UtilisateurForm utilisateurForm, @RequestHeader HttpHeaders headers){
        for (String key : headers.keySet()) {
            System.out.println( headers.get(key) );
        }
        return utilisateurService.add(utilisateurForm);
    }
    @PatchMapping (path = {"", "/", "/update"},params = {"idUtilisateur"})
    public UtilisateurDTO update(@RequestParam Long idUtilisateur, @Valid @RequestBody UtilisateurUpdateForm utilisateurUpdateForm, @RequestHeader HttpHeaders headers){
        for (String key : headers.keySet()) {
            System.out.println( headers.get(key) );
        }
        return utilisateurService.update(idUtilisateur, utilisateurUpdateForm);
    }

    @DeleteMapping(path = {"", "/", "/delete"},params = {"idUtilisateur"})
    public ResponseEntity<UtilisateurDTO> delete(@RequestParam Long idUtilisateur){
        return ResponseEntity
                .ok(utilisateurService.remove(idUtilisateur));
    }

    @GetMapping(path = {"/searchByName"}, params = {"nom"})
    @Operation(summary = "Recherche d'un utilisateur par nom")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Utilisateur trouvé", content = @Content(schema = @Schema(implementation = UtilisateurDTO.class))),
            @ApiResponse(responseCode = "404", description = "Utilisateur non trouvé")
    })
    public ResponseEntity<List<UtilisateurDTO>> searchByName(@RequestParam String nom) {
        return ResponseEntity.ok(utilisateurService.searchByName(nom));
    }

    /**
     * Recherche un utilisateur par son adresse e-mail.
     *
     * @return Une ResponseEntity contenant un utilisateur.
     */
    @GetMapping(path = {"/searchByMail"}, params = {"mail"})
    @Operation(summary = "Recherche d'un utilisateur par e-mail")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Utilisateur trouvé", content = @Content(schema = @Schema(implementation = UtilisateurDTO.class))),
            @ApiResponse(responseCode = "404", description = "Utilisateur non trouvé")
    })
    public ResponseEntity<UtilisateurDTO> findByMail(@RequestParam String mail) {
        return ResponseEntity.ok(utilisateurService.findByMail(mail));
    }
    @PostMapping(path = {"/login"})
    public ResponseEntity<UtilisateurDTO> login(@RequestBody Map<String, String> credentials) {
        String email = credentials.get("email");
        String password = credentials.get("password");

        // Ajouter des logs ici pour les informations d'identification
        logger.info("Login request received for email: {}", email);

        try {
            UtilisateurDTO utilisateurDTO = utilisateurService.login(email, password);
            // Ajouter des logs ici pour une connexion réussie
            logger.info("Login successful for email: {}", email);
            return ResponseEntity.ok(utilisateurDTO);
        } catch (AuthenticationException e) {
            // Ajouter des logs ici pour un échec de connexion
            logger.warn("Login failed for email: {}", email);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }


    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        // Appeler la méthode de déconnexion dans le service
        utilisateurService.logout();

        return ResponseEntity.ok("Déconnexion réussie");
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> handleNoSuchElementException(NoSuchElementException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }


}
