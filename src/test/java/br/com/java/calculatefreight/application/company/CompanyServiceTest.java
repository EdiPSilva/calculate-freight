package br.com.java.calculatefreight.application.company;
import br.com.java.calculatefreight.application.company.builders.CompanyEntityBuilder;
import br.com.java.calculatefreight.application.company.persistence.CompanyEntity;
import br.com.java.calculatefreight.application.company.persistence.CompanyRepository;
import br.com.java.calculatefreight.configuration.MessageConfiguration;
import br.com.java.calculatefreight.exceptions.CustomException;
import br.com.java.calculatefreight.utils.GenericValidations;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CompanyServiceTest {

    @InjectMocks
    private CompanyService companyService;

    @Mock
    private MessageConfiguration messageConfiguration;

    @Mock
    private GenericValidations genericValidations;

    @Mock
    private CompanyRepository companyRepository;

    @Test
    @DisplayName("Deve retornar erro quando não for localizado a empresa por id")
    public void shouldReturnErrorWhenNotFoundCompanyById(){
        final Long id = 1l;
        final Optional<CompanyEntity> optionalCompanyEntity = Optional.empty();
        when(companyRepository.findById(id)).thenReturn(optionalCompanyEntity);
        assertThrows(CustomException.class, () -> companyService.getCompanyEntityById(id));
    }

    @Test
    @DisplayName("Não deve retornar erro quando o registro for encontrado")
    public void shouldNotReturnErrorWhatTheRegistryWasFound() {
        final Long id = 1l;
        final CompanyEntity companyEntity = CompanyEntityBuilder.getInstance(id).getCompanyEntity();
        final Optional<CompanyEntity> optionalCompanyEntity = Optional.of(companyEntity);
        when(companyRepository.findById(id)).thenReturn(optionalCompanyEntity);
        compare(companyEntity, assertDoesNotThrow(() -> companyService.getCompanyEntityById(id)));
    }

    private void compare(final CompanyEntity companyEntityMock, final CompanyEntity companyEntityResult) {
        assertNotNull(companyEntityMock);
        assertNotNull(companyEntityResult);
        assertEquals(companyEntityMock.getId(), companyEntityResult.getId());
        assertEquals(companyEntityMock.getName(), companyEntityResult.getName());
        assertEquals(companyEntityMock.getDocument(), companyEntityResult.getDocument());
        assertEquals(companyEntityMock.getPostalCode(), companyEntityResult.getPostalCode());
        assertEquals(companyEntityMock.getActive(), companyEntityResult.getActive());
        assertEquals(companyEntityMock.getDateCreate(), companyEntityResult.getDateCreate());
    }
}
