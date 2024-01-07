package org.recipe.originalyrecipe.controllers;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.recipe.originalyrecipe.models.dto.UtilisateurDTO;
import org.recipe.originalyrecipe.services.UtilisateurService;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Date;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


class UtilisateurControllerTest {
    @Mock
    private UtilisateurService utilisateurService;

    @InjectMocks
    private UtilisateurController utilisateurController;

    private MockMvc mockMvc;
    @Test
    void getAll() throws Exception{
        // Initialiser les mocks
        MockitoAnnotations.initMocks(this);

        // Initialiser le MockMvc
        mockMvc = MockMvcBuilders.standaloneSetup(utilisateurController).build();

        // Créer des utilisateurs fictifs pour le test
        UtilisateurDTO utilisateurDTO1 = UtilisateurDTO.builder()
                .id(1L)
                .nom("George")
                .prenom("Lola")
                .dateDeNaissance(new Date())
                .pays("Belgique")
                .mail("george.lola@example.com")
                .build();
        UtilisateurDTO utilisateurDTO2 = UtilisateurDTO.builder()
                .id(2L)
                .nom("Lé")
                .prenom("Momo")
                .dateDeNaissance(new Date())
                .pays("France")
                .mail("le.momo@example.com")
                .build();

        // Définir le comportement attendu lors de l'appel de la méthode du service
        when(utilisateurService.getAll()).thenReturn(Arrays.asList(utilisateurDTO1, utilisateurDTO2));

        // Effectuer la requête GET vers votre endpoint
        mockMvc.perform(get("/utilisateur/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].nom").value("George"))
                .andExpect(jsonPath("$[0].prenom").value("Lola"))
                .andExpect(jsonPath("$[1].nom").value("Lé"))
                .andExpect(jsonPath("$[1].prenom").value("Momo"));



    }
}