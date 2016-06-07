package br.com.nutriapp.agendador;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Properties;

import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import br.com.nutriapp.ejb.DietaRN;
import br.com.nutriapp.ejb.UsuarioRN;
import br.com.nutriapp.model.entity.Alimento;
import br.com.nutriapp.model.entity.Dieta;
import br.com.nutriapp.model.entity.Refeicao;
import br.com.nutriapp.model.entity.Usuario;

@Stateless
public class NotificarRefeicao {

	@Inject
	DietaRN dietaRN;
	
	@Inject
	UsuarioRN usuarioRN;
	
	@Schedule(hour = "7", minute = "00")
	void cafeDaManha() {		
		for (Dieta dieta : dietaRN.listar()) {
			montarMensagem(dieta.getRefeicao("Café da Manhã"));
		}	
	}
	
	@Schedule(hour = "10", minute = "00")
	void lancheDaManha() {		
		for (Dieta dieta : dietaRN.listar()) {
			montarMensagem(dieta.getRefeicao("Lanche da Manhã"));
		}	
	}
	
	@Schedule(hour = "12", minute = "30")
	void almoco() {		
		for (Dieta dieta : dietaRN.listar()) {
			montarMensagem(dieta.getRefeicao("Almoço"));
		}	
	}
	
	@Schedule(hour = "15", minute = "30")
	void lancheDaTarde() {		
		for (Dieta dieta : dietaRN.listar()) {
			montarMensagem(dieta.getRefeicao("Lanche da Tarde"));
		}	
	}
	
	@Schedule(hour = "18", minute = "30")
	void jantar() {		
		for (Dieta dieta : dietaRN.listar()) {
			montarMensagem(dieta.getRefeicao("Jantar"));
		}	
	}
	
	@Schedule(hour = "22", minute = "00")
	void lancheDaNoite() {		
		for (Dieta dieta : dietaRN.listar()) {
			montarMensagem(dieta.getRefeicao("Lanche da Noite"));
		}	
	}
	
	public void montarMensagem(Refeicao refeicao) {
		
		List<Usuario> usuarios = usuarioRN.listarPor(refeicao.getDieta());

		if (!usuarios.isEmpty()) {

			String mensagem = "<h1>" + refeicao.getNome() + "</h1></br></br><ul>";
			for (Alimento alimento : refeicao.getAlimentos()) {
				mensagem += "<li>" + alimento.getNome() + "</li>";				
			}
			
			mensagem += "</ul>";

			String destinatarios = null;
			for (Usuario usuario : usuarios) {
				if (destinatarios == null) destinatarios = usuario.getEmail();
				else destinatarios += ", " + usuario.getEmail();
			}

			System.out.println("E-mail from: " + destinatarios);
			System.out.println("Mensagem:");
			System.out.println(mensagem);
			
			try {				
				enviarEmail(destinatarios, mensagem, refeicao.getNome());				
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
	
	public void enviarEmail(String email, String mensagem, String assunto) throws UnsupportedEncodingException {

		Properties props = new Properties();
		
        /** Parâmetros de conexão com servidor Gmail */
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");
		props.put("mail.smtp.starttls.enable", "true"); 
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.fallback", "false");  
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        
        String from = "nome@gmail.com";
        String fromName = "NutriApp";

        Session session = Session.getDefaultInstance(props,
               new javax.mail.Authenticator() {
                     protected PasswordAuthentication getPasswordAuthentication() {
                           return new PasswordAuthentication(from, "senha");
                     }
        });

        /** Ativa Debug para sessão */
        session.setDebug(true);

		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from, fromName));
			
			Address[] toUser = InternetAddress //Destinatário(s)
                     .parse(email);  

			message.setRecipients(Message.RecipientType.TO, toUser);

			message.setSubject(assunto);

			Multipart multipart = new MimeMultipart();
			BodyPart htmlPart = new MimeBodyPart();
			htmlPart.setContent(mensagem, "text/html; charset=ISO-8859-1");
			htmlPart.setDisposition(BodyPart.INLINE);
			multipart.addBodyPart(htmlPart);
			message.setContent(multipart);

			Transport.send(message);

		} catch (MessagingException mex) {
			mex.printStackTrace();
		}

	}
}
