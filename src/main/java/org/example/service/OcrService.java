package org.example.service;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.example.dto.OcrResultDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class OcrService {

    private static final Logger log = LoggerFactory.getLogger(OcrService.class);

    private final String dataPath;
    private final String language;

    public OcrService(
            @Value("${pretium.ocr.data-path:}") String dataPath,
            @Value("${pretium.ocr.language:por}") String language) {
        this.dataPath = dataPath;
        this.language = language;
    }

    public OcrResultDTO extractText(MultipartFile image) {
        if (image.isEmpty()) {
            throw new IllegalArgumentException("A imagem do cupom está vazia");
        }
        try {
            BufferedImage bufferedImage = ImageIO.read(image.getInputStream());
            if (bufferedImage == null) {
                throw new IllegalArgumentException("O arquivo não é uma imagem válida");
            }
            Tesseract tesseract = new Tesseract();
            if (!dataPath.isBlank()) {
                tesseract.setDatapath(dataPath);
            }
            tesseract.setLanguage(language);
            String text = tesseract.doOCR(bufferedImage).trim();
            return new OcrResultDTO(image.getOriginalFilename(), text, "SUCCESS");
        } catch (IOException | TesseractException exception) {
            throw new IllegalStateException("Não foi possível processar o OCR. Verifique Tesseract e tessdata.", exception);
        }
    }
}
