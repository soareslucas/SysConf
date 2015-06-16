/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ufg.inf.pw.utils;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.imageio.ImageIO;
import org.primefaces.model.UploadedFile;
import sun.misc.BASE64Encoder;

/**
 *
 * @author Lucas
 */
public class FotoUpload {

    public static String FileUploadToImg64(UploadedFile fotoUploaded) throws Exception{

        String imageString = null;

        if (fotoUploaded != null) {
            try {

                InputStream image = fotoUploaded.getInputstream();
                BufferedImage img = ImageIO.read(image);

                ByteArrayOutputStream bos = new ByteArrayOutputStream();

                ImageIO.write(img, "jpg", bos);
                byte[] imageBytes = bos.toByteArray();

                BASE64Encoder encoder = new BASE64Encoder();
                imageString = encoder.encode(imageBytes);

                bos.close();

            } catch (Exception e) {

                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                        FacesMessage.SEVERITY_ERROR, "Erro ao carregar foto, verifique compatibilidade do formato do arquivo!\n" + e.toString(), null));
                FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);

            }

        }
        return imageString;
    }

}
