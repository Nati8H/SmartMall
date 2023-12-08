package com.ngsolutions.SmartMall.service.impl;

import com.ngsolutions.SmartMall.model.dto.currency.CurrencyDTO;
import com.ngsolutions.SmartMall.service.CurrencyService;
import org.springframework.stereotype.Service;

import java.util.Currency;
import java.util.List;

@Service
public class CurrencyServiceImpl implements CurrencyService {

    @Override
    public List<CurrencyDTO> getAll(){
        return Currency.getAvailableCurrencies().stream().map(this::mapCurrencyToCurrencyDTO).toList();
    }

    public CurrencyDTO mapCurrencyToCurrencyDTO(Currency currency){
        CurrencyDTO currencyDTO = new CurrencyDTO();
        currencyDTO.setName(currency.getDisplayName());
        currencyDTO.setCurrencyCode(currency.getCurrencyCode());
        return currencyDTO;
    }
}
