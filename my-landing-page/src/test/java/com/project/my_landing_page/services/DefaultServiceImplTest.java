package com.project.my_landing_page.services;

import com.project.my_landing_page.models.Biografia;
import com.project.my_landing_page.repositories.DefaultRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DefaultServiceImplTest {

    @Mock
    private DefaultRepository<Biografia> repository;

    private DefaultService<Biografia> service;

    @BeforeEach
    void setUp() {
        service = new DefaultServiceImpl<>(repository) {};
    }

    @Test
    void deveSalvarEntidade() {
        Biografia nova = new Biografia(1L, "Teste");
        Biografia salva = new Biografia(2L, "Teste");
        when(repository.save(nova)).thenReturn(salva);

        Biografia res = service.save(nova);

        assertThat(res).isEqualTo(salva);
        verify(repository).save(nova);
    }

    @Test
    void deveListarTodasEntidades()  {
        Biografia bio1 = new Biografia(1L, "Teste1");
        Biografia bio2 = new Biografia(2L, "Teste2");
        when(repository.findAll()).thenReturn(List.of(bio1, bio2));

        List<Biografia> res = service.findAll();

        assertThat(res).containsExactly(bio1, bio2);
        verify(repository).findAll();
    }

    @Test
    void deveBuscarEntidadePorId() {
        Biografia bio = new Biografia(1L, "Teste");
        when(repository.findById(1L)).thenReturn(Optional.of(bio));

        Optional<Biografia> res = service.findById(1L);

        assertThat(res).containsSame(bio);
        verify(repository).findById(1L);
    }

    @Test
    void deveRetornarVazioQuandoEntidadeNaoExistir() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        Optional<Biografia> res = service.findById(1L);

        assertThat(res).isEmpty();
        verify(repository).findById(1L);
    }

    @Test
    void deveDeletarEntidadePorId() {
        service.deleteById(1L);

        verify(repository).deleteById(1L);
        verifyNoMoreInteractions(repository);
    }

    @Test
    void deveLancarExcecaoQuandoUpdateNaoForImplementado() {

        Throwable erro = catchThrowable(() -> service.update(1L, new Biografia()));

        assertThat(erro)
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Método não implementado");
        verifyNoInteractions(repository);
    }

}
