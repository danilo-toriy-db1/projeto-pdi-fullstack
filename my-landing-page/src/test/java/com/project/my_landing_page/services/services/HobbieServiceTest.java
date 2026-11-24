package com.project.my_landing_page.services.services;

import com.project.my_landing_page.models.Hobbie;
import com.project.my_landing_page.repositories.repositories.HobbieRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class HobbieServiceTest {

    @Mock
    private HobbieRepository hobbieRepository;

    @InjectMocks
    private HobbieService hobbieService;

    @Test
    void deveRetornarNuloQuandoHobbieNaoExistir() {
        when(hobbieRepository.findById(1L)).thenReturn(Optional.empty());

        Hobbie res = hobbieService.update(1L, new Hobbie(0L, "Teste"));

        assertThat(res).isNull();
        verify(hobbieRepository).findById(1L);
        verify(hobbieRepository, never()).save(any());
    }

    @Test
    void deveAtualizarDesgostoQuandoExistir() {
        Hobbie existente = new Hobbie(1L, "Antiga");
        Hobbie nova = new Hobbie(2L, "Nova");
        when(hobbieRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(hobbieRepository.save(any(Hobbie.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Hobbie res = hobbieService.update(1L, nova);

        ArgumentCaptor<Hobbie> captor = ArgumentCaptor.forClass(Hobbie.class);
        verify(hobbieRepository).findById(1L);
        verify(hobbieRepository).save(captor.capture());
        Hobbie salvo = captor.getValue();
        assertEquals(1L, salvo.getId());
        assertEquals("Nova", salvo.getHobbie());
        verifyNoMoreInteractions(hobbieRepository);
    }
}
