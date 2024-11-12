package com.bnbcurrencyservice.fetcher.service;

import com.bnbcurrencyservice.fetcher.model.CurrencyRate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.w3c.dom.*;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class XmlParserService {

    public List<CurrencyRate> parseXmlData(String xmlData) throws Exception {
        List<CurrencyRate> rates = new ArrayList<>();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        Document doc = builder.parse(new InputSource(new StringReader(xmlData)));
        doc.getDocumentElement().normalize();

        NodeList rowNodes = doc.getElementsByTagName("ROW");

        for (int i = 0; i < rowNodes.getLength(); i++) {
            Node rowNode = rowNodes.item(i);

            if (rowNode.getNodeType() == Node.ELEMENT_NODE) {
                Element rowElement = (Element) rowNode;
                rates.add(createCurrencyRate(rowElement));
            }
        }

        return rates;
    }

    private CurrencyRate createCurrencyRate(Element rowElement) {
        CurrencyRate rate = new CurrencyRate();

        rate.setCode(getElementValue(rowElement, "CODE"));
        rate.setCurrDate(parseDate(getElementValue(rowElement, "CURR_DATE")));
        rate.setExtraInfo(getElementValue(rowElement, "EXTRAINFO"));
        rate.setFStar(getElementValue(rowElement, "F_STAR"));
        rate.setGold(getElementValue(rowElement, "GOLD"));
        rate.setNameBg(getElementValue(rowElement, "NAME_BG"));
        rate.setNameEn(getElementValue(rowElement, "NAME_EN"));
        rate.setTitle(getElementValue(rowElement, "TITLE"));
        rate.setRate(parseDecimal(getElementValue(rowElement, "RATE")));
        rate.setRatio(parseInt(getElementValue(rowElement, "RATIO")));
        rate.setReverseRate(parseDecimal(getElementValue(rowElement, "REVERSERATE")));
        rate.setDownloadTime(LocalDateTime.now());

        return rate;
    }

    private String getElementValue(Element parent, String tagName) {
        NodeList nodeList = parent.getElementsByTagName(tagName);
        if (nodeList.getLength() > 0) {
            return nodeList.item(0).getTextContent();
        }
        return null;
    }

    private LocalDate parseDate(String dateStr) {
        if (dateStr == null || dateStr.trim().isEmpty()) {
            return null;
        }
        try {
            return LocalDate.parse(dateStr);
        } catch (Exception e) {
            log.warn("Failed to parse date: {}", dateStr);
            return null;
        }
    }

    private BigDecimal parseDecimal(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        try {
            return new BigDecimal(value.replace(",", "."));
        } catch (Exception e) {
            log.warn("Failed to parse decimal: {}", value);
            return null;
        }
    }

    private Integer parseInt(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        try {
            return Integer.parseInt(value);
        } catch (Exception e) {
            log.warn("Failed to parse integer: {}", value);
            return null;
        }
    }
}