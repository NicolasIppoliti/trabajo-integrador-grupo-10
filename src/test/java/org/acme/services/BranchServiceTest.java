package org.acme.services;

import java.util.ArrayList;
import java.util.List;

import org.acme.domain.Branch;
import org.acme.mappers.BranchMapper;
import org.acme.models.entities.BranchEntity;
import org.acme.repositories.BranchRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.mockito.Mockito;

import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@QuarkusTest
public class BranchServiceTest {

    @Inject
    BranchService branchService;

    @InjectMock
    BranchRepository branchRepository;

    @Inject
    EntityManager entityManager;

    @InjectMock
    BranchMapper mapper;

    @BeforeEach
    public void setup() {
        entityManager = mock(EntityManager.class);
        when(branchRepository.getEntityManager()).thenReturn(entityManager);
        Branch branch = new Branch();
        branch.setName("Sucursal");
        branch.setAddress("dire 1234");
        branch.setCity("Moron");
    }

    @Test
    @DisplayName("Test getAll() method")
    public void testGetAllBranches() {
        // Mock repository response
        List<BranchEntity> mockEntities = new ArrayList<>();
        mockEntities.add(new BranchEntity(1L, "Branch 1", "Address 1", "City 1"));
        mockEntities.add(new BranchEntity(2L, "Branch 2", "Address 2", "City 2"));
        mockEntities.add(new BranchEntity(3L, "Branch 3", "Address 3", "City 3"));
        Mockito.when(branchRepository.listAll()).thenReturn(mockEntities);

        // Mock mapper response
        Mockito.when(mapper.toDomain(Mockito.any(BranchEntity.class))).thenAnswer(invocation -> {
            BranchEntity entity = invocation.getArgument(0);
            return new Branch(entity.getId(), entity.getName(), entity.getAddress(), entity.getCity());
        });

        // Call service method
        List<Branch> branches = branchService.getAll();

        // Assertions
        assertNotNull(branches);
        assertEquals(3, branches.size());
        assertEquals("Branch 1", branches.get(0).getName());
        assertEquals("Address 2", branches.get(1).getAddress());

        // Verify repository method invocation
        Mockito.verify(branchRepository, Mockito.times(1)).listAll();
    }

    @Test
    @DisplayName("Test getById() method")
    public void testGetBranchById() {
        // Mock repository response
        BranchEntity mockEntity = new BranchEntity(1L, "Branch 1", "Address 1", "City 1");
        Mockito.when(branchRepository.findById(1L)).thenReturn(mockEntity);

        // Mock mapper response
        Mockito.when(mapper.toDomain(mockEntity)).thenReturn(new Branch(1L, "Branch 1", "Address 1", "City 1"));

        // Call service method
        Branch branch = branchService.getById(1L);

        // Assertions
        assertNotNull(branch);
        assertEquals(1L, branch.getId());
        assertEquals("Branch 1", branch.getName());
        assertEquals("Address 1", branch.getAddress());

        // Verify repository method invocation
        Mockito.verify(branchRepository, Mockito.times(1)).findById(1L);
    }

    // @Test
    // @DisplayName("Test create() method")
    // @Transactional
    // public void testCreateBranch() {
    //     // Mock input
    //     Branch branch = new Branch(1L, "New Branch", "New Address", "New City");

    //     // Mock mapper response
    //     BranchEntity mockEntity = new BranchEntity(1L, "New Branch", "New Address", "New City");
    //     Mockito.when(mapper.toEntity(branch)).thenReturn(mockEntity);
    //     System.out.println("DTO: " + branch);
    //     System.out.println("Entity: " + mockEntity);

    //     // Mock repository behavior
    //    // Mockito.doNothing().when(entityManager).merge(Mockito.any(BranchEntity.class));
    //     Mockito.doNothing().when(branchRepository).persist(Mockito.any(BranchEntity.class));

    //     // Call service method
    //     Branch createdBranch = branchService.create(branch);

    //     // Assertions
    //     System.out.println("Created:" + createdBranch);
    //     assertNotNull(createdBranch);
    //     assertEquals(1L, createdBranch.getId());
    //     assertEquals("New Branch", createdBranch.getName());
    //     assertEquals("New Address", createdBranch.getAddress());
    //     assertEquals("New City", createdBranch.getCity());


    //     // Verify repository method invocation
    //     verify(branchRepository, times(1)).persist(any(BranchEntity.class));
    // }

    // @Test
    // @DisplayName("Test update() method")
    // @Transactional
    // public void testUpdateBranch() {
    //     // Mock input
    //     Branch updatedBranch = new Branch(1L, "Updated Branch", "Updated Address", "Updated City");

    //     // Mock repository response
    //     BranchEntity existingEntity = new BranchEntity(1L, "Old Branch", "Old Address", "Old City");
    //     Mockito.when(branchRepository.findById(1L)).thenReturn(existingEntity);

    //     // Mock mapper response
    //     BranchEntity updatedEntity = new BranchEntity(1L, "Updated Branch", "Updated Address", "Updated City");
    //     Mockito.when(mapper.toEntity(updatedBranch)).thenReturn(updatedEntity);

    //     // Call service method
    //     Branch updated = branchService.update(1L, updatedBranch);

    //     // Assertions
    //     assertNotNull(updated);
    //     assertEquals(1L, updated.getId());
    //     assertEquals("Updated Branch", updated.getName());
    //     assertEquals("Updated Address", updated.getAddress());

    //     // Verify repository method invocation
    //     Mockito.verify(branchRepository, Mockito.times(1)).findById(1L);
    //     Mockito.verify(branchRepository, Mockito.times(1)).persist(any(BranchEntity.class));
    // }

    @Test
    @DisplayName("Test delete() method")
    @Transactional
    public void testDeleteBranch() {
        // Mock repository response
        BranchEntity existingEntity = new BranchEntity(1L, "Branch to delete", "Address", "City");
        Mockito.when(branchRepository.findById(1L)).thenReturn(existingEntity);

        // Call service method
        boolean deleted = branchService.delete(1L);

        // Assertions
        assertTrue(deleted);

        // Verify repository method invocation
        Mockito.verify(branchRepository, Mockito.times(1)).findById(1L);
        Mockito.verify(branchRepository, Mockito.times(1)).delete(any(BranchEntity.class));
    }
}
