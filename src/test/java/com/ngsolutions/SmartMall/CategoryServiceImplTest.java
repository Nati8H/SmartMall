package com.ngsolutions.SmartMall;

import com.ngsolutions.SmartMall.model.dto.category.CategoriesAddDTO;
import com.ngsolutions.SmartMall.model.dto.category.CategoryDisplayDTO;
import com.ngsolutions.SmartMall.model.entity.Category;
import com.ngsolutions.SmartMall.model.entity.User;
import com.ngsolutions.SmartMall.repo.CategoryRepository;
import com.ngsolutions.SmartMall.repo.UserRepository;
import com.ngsolutions.SmartMall.service.impl.CategoryServiceImpl;
import com.ngsolutions.SmartMall.utils.ImageEncryptor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CategoryServiceImplTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ImageEncryptor imageEncryptor;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddCategory() throws IOException {
        // Arrange
        UserDetails user = createUserDetails();
        CategoriesAddDTO categoriesAddDTO = createCategoriesAddDTO();
        User userEntity = createUserEntity();
        Category category = createCategory();

        when(userRepository.findByEmail(user.getUsername())).thenReturn(Optional.of(userEntity));
        when(categoryRepository.save(any(Category.class))).thenReturn(category);

        // Act
        categoryService.add(categoriesAddDTO, user);

        // Assert
        verify(categoryRepository, times(1)).save(any(Category.class));
    }

    @Test
    void testUpdateCategory() throws IOException {
        // Arrange
        CategoriesAddDTO categoriesAddDTO = createCategoriesAddDTO();
        Category oldCategory = createCategory();
        Category updatedCategory = createCategory();
        updatedCategory.setName("UpdatedName");

        when(categoryRepository.findById(categoriesAddDTO.getId())).thenReturn(Optional.of(oldCategory));
        when(categoryRepository.save(any(Category.class))).thenReturn(updatedCategory);

        // Act
        categoryService.update(categoriesAddDTO, createUserDetails());

        // Assert
        verify(categoryRepository, times(1)).save(any(Category.class));
        assertEquals("UpdatedName", updatedCategory.getName());
    }

    @Test
    void testDeleteCategory() {
        // Arrange
        long categoryId = 1L;

        // Act
        categoryService.delete(categoryId);

        // Assert
        verify(categoryRepository, times(1)).deleteById(categoryId);
    }

    @Test
    void testGetById() {
        // Arrange
        long categoryId = 1L;
        Category category = createCategory();

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));

        // Act
        CategoryDisplayDTO result = categoryService.getById(categoryId);

        // Assert
        assertNotNull(result);
        assertEquals(category.getId(), result.getId());
    }

    @Test
    void testGetFirstAvailableCategory() {
        // Arrange
        List<Category> categories = List.of(createCategory());
        when(categoryRepository.findAll()).thenReturn(categories);

        // Act
        CategoryDisplayDTO result = categoryService.getFirstAvailableCategory();

        // Assert
        assertNotNull(result);
        assertEquals(categories.get(0).getId(), result.getId());
    }

    @Test
    void testGetCategoriesAddDTOById() {
        // Arrange
        long categoryId = 1L;
        Category category = createCategory();

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));

        // Act
        CategoriesAddDTO result = categoryService.getCategoriesAddDTOById(categoryId);

        // Assert
        assertNotNull(result);
        assertEquals(category.getId(), result.getId());
    }

    @Test
    void testGetAllCategories() {
        // Arrange
        List<Category> categories = List.of(createCategory());
        when(categoryRepository.findAll()).thenReturn(categories);

        // Act
        List<CategoryDisplayDTO> result = categoryService.getAll();

        // Assert
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(categories.size(), result.size());
    }

    public static UserDetails createUserDetails() {
        // Create and return a UserDetails mock as needed
        return new org.springframework.security.core.userdetails.User(
                "testuser@example.com",
                "password",
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))
        );
    }

    public static CategoriesAddDTO createCategoriesAddDTO() {
        // Create and return a CategoriesAddDTO mock as needed
        CategoriesAddDTO categoriesAddDTO = new CategoriesAddDTO();
        categoriesAddDTO.setId(1L);
        categoriesAddDTO.setName("Test Category");
        categoriesAddDTO.setDescription("Test Description");
        byte[] bytes = ("base64encodedphotodata").getBytes();
        categoriesAddDTO.setPhoto(new MockMultipartFile("base64encodedphotodata", bytes));
        return categoriesAddDTO;
    }

    public static User createUserEntity() {
        // Create and return a User entity mock as needed
        User user = new User();
        user.setId(1L);
        user.setEmail("testuser@example.com");
        user.setUsername("testuser");
        // Add any additional properties as needed
        return user;
    }

    public static Category createCategory() {
        // Create and return a Category entity mock as needed
        Category category = new Category();
        category.setId(1L);
        category.setName("Test Category");
        category.setDescription("Test Description");
        byte[] bytes = ("base64encodedphotodata").getBytes();
        category.setPhoto(bytes);
        return category;
    }
}

