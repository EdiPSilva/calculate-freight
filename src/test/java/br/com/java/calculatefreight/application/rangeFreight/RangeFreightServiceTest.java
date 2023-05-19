package br.com.java.calculatefreight.application.rangeFreight;
import br.com.java.calculatefreight.application.calculationTypeRangeFreight.builders.CalculationTypeDtoBuilder;
import br.com.java.calculatefreight.application.calculationTypeRangeFreight.builders.CalculationTypeRangeFreightEntityBuilder;
import br.com.java.calculatefreight.application.calculationTypeRangeFreight.persistence.CalculationTypeDto;
import br.com.java.calculatefreight.application.calculationTypeRangeFreight.persistence.CalculationTypeRangeFreightEntity;
import br.com.java.calculatefreight.application.rangeFreight.persistence.RangeFreightDto;
import br.com.java.calculatefreight.application.rangeFreight.persistence.RangeFreightEntity;
import br.com.java.calculatefreight.application.rangeFreight.persistence.RangeFreightRepository;
import br.com.java.calculatefreight.configuration.MessageConfiguration;
import br.com.java.calculatefreight.exceptions.CustomException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RangeFreightServiceTest {

    @InjectMocks
    private RangeFreightService rangeFreightService;

    @Mock
    private MessageConfiguration messageConfiguration;

    @Mock
    private RangeFreightRepository rangeFreightRepository;

    @Test
    @DisplayName("Deve retornar erro quando não for localizado o range de frete")
    public void shouldReturnErrorWhenNotFoundTheRangeFreight(){
        final CalculationTypeDto calculationTypeDto = CalculationTypeDtoBuilder.getInstance().getCalculationTypeDto();
        final CalculationTypeRangeFreightEntity calculationTypeRangeFreightEntity = CalculationTypeRangeFreightEntityBuilder.getInstance().getCalculationTypeRangeFreightEntity();
        final List<CalculationTypeRangeFreightEntity> calculationTypeRangeFreightEntityList = Arrays.asList(calculationTypeRangeFreightEntity);
        final List<Long> idList = Arrays.asList(calculationTypeRangeFreightEntity.getId());
        final List<RangeFreightEntity> rangeFreightEntityList = new ArrayList<>();
        when(rangeFreightRepository.findRangeFreight(idList)).thenReturn(rangeFreightEntityList);
        assertThrows(CustomException.class, () -> rangeFreightService.getFreightValue(calculationTypeDto, calculationTypeRangeFreightEntityList));
    }

    @Test
    @DisplayName("Deve retornar erro quando a transportadora estiver inativa")
    public void shouldReturnErrorWhenTheShippingCompanyIsInactive(){
        final CalculationTypeDto calculationTypeDto = CalculationTypeDtoBuilder.getInstance().getCalculationTypeDto();
        final CalculationTypeRangeFreightEntity calculationTypeRangeFreightEntity = CalculationTypeRangeFreightEntityBuilder.getInstance().getCalculationTypeRangeFreightEntity();
        calculationTypeRangeFreightEntity.getRangeFreightEntity().getShippingCompanyEntity().setActive(false);
        final List<CalculationTypeRangeFreightEntity> calculationTypeRangeFreightEntityList = Arrays.asList(calculationTypeRangeFreightEntity);
        final List<Long> idList = Arrays.asList(calculationTypeRangeFreightEntity.getId());
        final List<RangeFreightEntity> rangeFreightEntityList = Arrays.asList(calculationTypeRangeFreightEntity.getRangeFreightEntity());
        when(rangeFreightRepository.findRangeFreight(idList)).thenReturn(rangeFreightEntityList);
        assertThrows(CustomException.class, () -> rangeFreightService.getFreightValue(calculationTypeDto, calculationTypeRangeFreightEntityList));
    }

    @Test
    @DisplayName("Não deve retornar erro quando localizado range de frete com o intervalo de faixa")
    public void shouldNotReturnErrorWhenFoundRangeFreightWithTheStripInterval(){
        final CalculationTypeDto calculationTypeDto = CalculationTypeDtoBuilder.getInstance().getCalculationTypeDto();
        final CalculationTypeRangeFreightEntity calculationTypeRangeFreightEntity = CalculationTypeRangeFreightEntityBuilder.getInstance().getCalculationTypeRangeFreightEntity();
        final RangeFreightEntity rangeFreightEntity = calculationTypeRangeFreightEntity.getRangeFreightEntity();
        setStartAndEndValue(calculationTypeDto, rangeFreightEntity);
        calculationTypeRangeFreightEntity.setRangeFreightEntity(rangeFreightEntity);
        final List<CalculationTypeRangeFreightEntity> calculationTypeRangeFreightEntityList = Arrays.asList(calculationTypeRangeFreightEntity);
        final List<Long> idList = Arrays.asList(calculationTypeRangeFreightEntity.getId());
        final List<RangeFreightEntity> rangeFreightEntityList = Arrays.asList(calculationTypeRangeFreightEntity.getRangeFreightEntity());
        when(rangeFreightRepository.findRangeFreight(idList)).thenReturn(rangeFreightEntityList);
        compare(rangeFreightEntity, assertDoesNotThrow(() -> rangeFreightService.getFreightValue(calculationTypeDto, calculationTypeRangeFreightEntityList)), rangeFreightEntity.getFreightValue());
    }

    private void setStartAndEndValue(final CalculationTypeDto calculationTypeDto, final RangeFreightEntity rangeFreightEntity) {
        final Double startValue = calculationTypeDto.getValue() - 1;
        final Double endValue = calculationTypeDto.getValue() + 1;
        rangeFreightEntity.setStartValue(startValue <= 0 ? 0D : startValue);
        rangeFreightEntity.setEndValue(endValue);
    }

    private void compare(final RangeFreightEntity rangeFreightEntity, final RangeFreightDto rangeFreightDto, final Double freightValue) {
        assertNotNull(rangeFreightEntity);
        assertNotNull(rangeFreightDto);
        assertNotNull(rangeFreightDto.getRangeFreightEntity());
        assertNotNull(rangeFreightDto.getFreightValue());
        assertEquals(rangeFreightDto.getFreightValue(), freightValue);
        final RangeFreightEntity rangeFreightEntityResult = rangeFreightDto.getRangeFreightEntity();
        assertEquals(rangeFreightEntity.getId(), rangeFreightEntityResult.getId());
        assertEquals(rangeFreightEntity.getShippingCompanyEntity().getId(), rangeFreightEntityResult.getShippingCompanyEntity().getId());
        assertEquals(rangeFreightEntity.getStartValue(), rangeFreightEntityResult.getStartValue());
        assertEquals(rangeFreightEntity.getEndValue(), rangeFreightEntityResult.getEndValue());
        assertEquals(rangeFreightEntity.getFreightValue(), rangeFreightEntityResult.getFreightValue());
        assertEquals(rangeFreightEntity.getSurplusValue(), rangeFreightEntityResult.getSurplusValue());
        assertEquals(rangeFreightEntity.getActive(), rangeFreightEntityResult.getActive());
        assertEquals(rangeFreightEntity.getDateCreate(), rangeFreightEntityResult.getDateCreate());
    }

    @Test
    @DisplayName("Não deve retornar erro quando não localizado range de frete com o intervalo de faixa")
    public void shouldNotReturnErrorWhenNotFoundRangeFreightWithTheStripInterval(){
        final CalculationTypeDto calculationTypeDto = CalculationTypeDtoBuilder.getInstance().getCalculationTypeDto();
        final CalculationTypeRangeFreightEntity calculationTypeRangeFreightEntity = CalculationTypeRangeFreightEntityBuilder.getInstance().getCalculationTypeRangeFreightEntity();
        final List<CalculationTypeRangeFreightEntity> calculationTypeRangeFreightEntityList = Arrays.asList(calculationTypeRangeFreightEntity);
        final List<Long> idList = Arrays.asList(calculationTypeRangeFreightEntity.getId());
        final List<RangeFreightEntity> rangeFreightEntityList = Arrays.asList(calculationTypeRangeFreightEntity.getRangeFreightEntity());
        when(rangeFreightRepository.findRangeFreight(idList)).thenReturn(rangeFreightEntityList);
        final Double freightValue = calculateSurplusValueAboutFreightValue(calculationTypeRangeFreightEntity.getRangeFreightEntity().getFreightValue(), calculationTypeRangeFreightEntity.getRangeFreightEntity().getSurplusValue());
        compare(calculationTypeRangeFreightEntity.getRangeFreightEntity(), assertDoesNotThrow(() -> rangeFreightService.getFreightValue(calculationTypeDto, calculationTypeRangeFreightEntityList)), freightValue);
    }

    private Double calculateSurplusValueAboutFreightValue(Double freightValue, final Double surplusValue) {
        freightValue += (freightValue / 100) * surplusValue;
        final BigDecimal bigDecimal = new BigDecimal(freightValue).setScale(2, RoundingMode.HALF_UP);
        return bigDecimal.doubleValue();
    }
}
