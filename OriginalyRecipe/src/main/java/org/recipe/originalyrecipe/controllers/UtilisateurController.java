package org.recipe.originalyrecipe.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.recipe.originalyrecipe.models.dto.UtilisateurDTO;
import org.recipe.originalyrecipe.models.form.UtilisateurForm;
import org.recipe.originalyrecipe.services.UtilisateurService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
}
