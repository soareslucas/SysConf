// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FotoUpload.java

package ufg.inf.pw.utils;

import java.io.ByteArrayOutputStream;
import javax.faces.application.FacesMessage;
import javax.faces.context.*;
import javax.imageio.ImageIO;
import org.primefaces.model.UploadedFile;
import sun.misc.BASE64Encoder;

public class FotoUpload
{

    public FotoUpload()
    {
    }

    public static String FileUploadToImg64(UploadedFile fotoUploaded)
        throws Exception
    {
        String imageString = null;
        if(fotoUploaded != null)
            try
            {
                java.io.InputStream image = fotoUploaded.getInputstream();
                java.awt.image.BufferedImage img = ImageIO.read(image);
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                ImageIO.write(img, "jpg", bos);
                byte imageBytes[] = bos.toByteArray();
                BASE64Encoder encoder = new BASE64Encoder();
                imageString = encoder.encode(imageBytes);
                bos.close();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, (new StringBuilder()).append("A foto ").append(fotoUploaded.getFileName()).append(" foi carregada.").toString(), null));
                FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
            }
            catch(Exception e)
            {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, (new StringBuilder()).append("Erro ao carregar foto, verifique compatibilidade do formato do arquivo!\n").append(e.toString()).toString(), null));
                FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
            }
        return imageString;
    }
}
