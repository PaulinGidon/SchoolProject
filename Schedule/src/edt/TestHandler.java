/*package edt;

import com.sun.net.httpserver.HttpExchange;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.net.URI;

import edt.CustomHandler;

import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.*;

public class TestHandler {


    //Pour tester le Handle nous avons besoin de mocker pour le httpExchange
    @Test
    public void testHandle() throws Exception{

        //on créer les mocks , on en crée 2 pour varier les test
        HttpExchange exchangeTEST = mock(HttpExchange.class);
         HttpExchange exchangeTEST2 = mock(HttpExchange.class);

        //comme pour les autres test on sauvegarde la réponse pour réaliser des Assert dessus
        ByteArrayOutputStream reptest = new ByteArrayOutputStream();
        ByteArrayOutputStream reptest2 = new ByteArrayOutputStream();

        //Le test va se dérouler sur une simulation d'une requete d'url
        when(exchangeTEST.getRequestMethod()).thenReturn("GET");
        //on simule bien le faux URL
        when(exchangeTEST.getRequestURI()).thenReturn(new URI("/edt?semaine=6&groupe=4"));

        //on simule un deuxième un peu différent
        when(exchangeTEST2.getRequestMethod()).thenReturn("GET");
        
        when(exchangeTEST2.getRequestURI()).thenReturn(new URI("/edt?semaine=10"));


        new CustomHandler().handle(exchangeTEST);
        new CustomHandler().handle(exchangeTEST2);

        //on stock ce qu'il y'a a tester dans un sting
        String reponse = reptest.toString("UTF-8");
        String reponse2 = reptest2.toString("UTF-8");
        
        //ces deux lignes comme pour le Handler original , vont s'assurer que la requete est bien en charge
        URI requestedUriTEST = exchangeTEST.getRequestURI();
        String query = requestedUriTEST.getQuery();

        URI requestedUriTEST2 = exchangeTEST2.getRequestURI();
        String query2 = requestedUriTEST2.getQuery();

        //premier mock

        assert(reponse.contains("semaine=6"));
        assert(reponse.contains("groupe=4"));
        //on s'asure de la bonne séparation de la requete
        assert(reponse.contains("&"));

        //deuxième mock

        
        assert(reponse2.contains("semaine=10"));
        assert(reponse2.contains("&"));
        assertFalse(reponse2.contains("groupe=4"));

        
        

    }

}
*/