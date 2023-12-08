package com.ngsolutions.SmartMall.service;

import com.ngsolutions.SmartMall.model.dto.currency.CurrencyDTO;

import java.util.Currency;
import java.util.List;
import java.util.Set;

public interface CurrencyService {

    List<CurrencyDTO> getAll();
}
