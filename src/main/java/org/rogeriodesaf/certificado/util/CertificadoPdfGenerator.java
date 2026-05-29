package org.rogeriodesaf.certificado.util;

import org.rogeriodesaf.certificado.dto.CertificadoResponseDTO;

import java.nio.charset.StandardCharsets;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public final class CertificadoPdfGenerator {

    private static final DateTimeFormatter EMISSION_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private CertificadoPdfGenerator() {
    }

    public static byte[] generate(CertificadoResponseDTO certificado) {
        String emissionDate = certificado.dataEmissao().format(EMISSION_FORMATTER);
        String content = String.join("\n",
                "q",
                "0.62 0.42 0.13 RG",
                "4 w",
                "38 38 519 766 re S",
                "0.85 0.73 0.48 RG",
                "1.5 w",
                "54 54 487 734 re S",
                "BT /F2 14 Tf 0.62 0.42 0.13 rg 180 772 Td (CERTIFICADO DE CONCLUSAO) Tj ET",
                "BT /F3 27 Tf 0.18 0.11 0.07 rg 88 706 Td (Suporte de Aprendizado Biblico) Tj ET",
                "BT /F3 23 Tf 0.18 0.11 0.07 rg 142 674 Td (e Ensino Reformado) Tj ET",
                "BT /F1 16 Tf 0.35 0.24 0.14 rg 215 624 Td (Certificamos que) Tj ET",
                textCommand("F3", 25, 140, 572, certificado.nomeAluno()),
                "BT /F1 16 Tf 0.22 0.15 0.10 rg 94 520 Td (concluiu com aproveitamento o curso) Tj ET",
                textCommand("F2", 20, 86, 488, certificado.tituloCurso()),
                "BT /F1 13 Tf 0.28 0.19 0.11 rg 102 410 Td (Codigo de validacao:) Tj ET",
                textCommand("F2", 13, 250, 410, certificado.codigoValidacao()),
                "BT /F1 13 Tf 0.28 0.19 0.11 rg 102 384 Td (Data de emissao:) Tj ET",
                textCommand("F2", 13, 214, 384, emissionDate),
                "0.45 0.30 0.16 RG",
                "1 w",
                "86 250 m 246 250 l S",
                "334 250 m 494 250 l S",
                "BT /F1 12 Tf 0.28 0.19 0.11 rg 104 232 Td (Coordenacao Academica) Tj ET",
                "BT /F1 12 Tf 0.28 0.19 0.11 rg 370 232 Td (Direcao da Plataforma) Tj ET",
                "BT /F1 11 Tf 0.45 0.30 0.16 rg 188 144 Td (Documento emitido digitalmente para fins de validacao academica.) Tj ET",
                "Q"
        );

        return buildPdf(content);
    }

    private static byte[] buildPdf(String content) {
        List<String> objects = List.of(
                "<< /Type /Catalog /Pages 2 0 R >>",
                "<< /Type /Pages /Count 1 /Kids [3 0 R] >>",
                "<< /Type /Page /Parent 2 0 R /MediaBox [0 0 595 842] /Resources << /Font << /F1 4 0 R /F2 5 0 R /F3 6 0 R >> >> /Contents 7 0 R >>",
                "<< /Type /Font /Subtype /Type1 /BaseFont /Helvetica >>",
                "<< /Type /Font /Subtype /Type1 /BaseFont /Helvetica-Bold >>",
                "<< /Type /Font /Subtype /Type1 /BaseFont /Times-Bold >>",
                "<< /Length " + content.getBytes(StandardCharsets.US_ASCII).length + " >>\nstream\n" + content + "\nendstream"
        );

        StringBuilder pdf = new StringBuilder("%PDF-1.4\n");
        List<Integer> offsets = new ArrayList<>();
        offsets.add(0);

        for (int index = 0; index < objects.size(); index++) {
            offsets.add(pdf.length());
            pdf.append(index + 1)
                    .append(" 0 obj\n")
                    .append(objects.get(index))
                    .append("\nendobj\n");
        }

        int xrefOffset = pdf.length();
        pdf.append("xref\n0 ")
                .append(objects.size() + 1)
                .append('\n')
                .append("0000000000 65535 f \n");

        for (int index = 1; index < offsets.size(); index++) {
            pdf.append(String.format("%010d 00000 n %n", offsets.get(index)));
        }

        pdf.append("trailer << /Size ")
                .append(objects.size() + 1)
                .append(" /Root 1 0 R >>\nstartxref\n")
                .append(xrefOffset)
                .append("\n%%EOF");

        return pdf.toString().getBytes(StandardCharsets.US_ASCII);
    }

    private static String textCommand(String font, int fontSize, int x, int y, String value) {
        return "BT /" + font + ' ' + fontSize + " Tf 0.18 0.11 0.07 rg " + x + ' ' + y + " Td (" + escape(value) + ") Tj ET";
    }

    private static String escape(String value) {
        return value
                .replace("\\", "\\\\")
                .replace("(", "\\(")
                .replace(")", "\\)");
    }
}
