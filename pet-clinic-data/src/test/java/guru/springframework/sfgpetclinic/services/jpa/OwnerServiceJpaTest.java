package guru.springframework.sfgpetclinic.services.jpa;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.repositories.OwnerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OwnerServiceJpaTest {

    private static final String LAST_NAME = "Smith";

    @Mock
    OwnerRepository ownerRepository;

    @InjectMocks
    OwnerServiceJpa service;

    private Owner owner1;

    @BeforeEach
    void setUp() {
        owner1 = Owner.builder().id(1L).build();
    }

    @Test
    void findByLastName() {
        Owner returnOwner = Owner.builder().id(1L).lastName(LAST_NAME).build();
        when(ownerRepository.findByLastName(any())).thenReturn(returnOwner);

        Owner actualOwner = service.findByLastName(LAST_NAME);

        assertEquals(LAST_NAME, actualOwner.getLastName());
        verify(ownerRepository).findByLastName(any());
    }

    @Test
    void findAll() {
        Set<Owner> expectedOwners = new HashSet<>();
        expectedOwners.add(Owner.builder().id(1L).build());
        expectedOwners.add(Owner.builder().id(2L).build());

        when(ownerRepository.findAll()).thenReturn(expectedOwners);

        Set<Owner> actualOwners = service.findAll();

        assertNotNull(actualOwners);
        assertEquals(expectedOwners.size(), actualOwners.size());
    }

    @Test
    void findById() {
        when(ownerRepository.findById(anyLong())).thenReturn(Optional.of(owner1));
        Owner owner = service.findById(1L);
        assertNotNull(owner);
    }

    @Test
    void save() {
        Owner ownerToSave = Owner.builder().id(1L).build();
        when(ownerRepository.save(any())).thenReturn(owner1);

        Owner savedOwner = service.save(ownerToSave);

        assertNotNull(savedOwner);
    }

    @Test
    void delete() {
        service.delete(owner1);
        verify(ownerRepository).delete(owner1);
    }

    @Test
    void deleteById() {
        service.deleteById(1L);
        verify(ownerRepository).deleteById(1L);
    }
}