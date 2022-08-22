package view;

import java.util.Scanner;

public class InputOutput {
	
		private static Scanner lettore = creaScanner();
		  
		  private static Scanner creaScanner ()
		  {
		   Scanner creato = new Scanner(System.in);
		   creato.useDelimiter(System.getProperty("line.separator"));
		   return creato;
		  }
		  
		  public static String leggiStringa (String messaggio)
		  {
			  InputOutput.mostraMessaggio(messaggio);
			  return lettore.next();
		  }
		  
		  public static String leggiStringaNonVuota(String messaggio)
		  {
		   boolean finito=false;
		   String lettura = null;
		   do
		   {
			 lettura = leggiStringa(messaggio);
			 lettura = lettura.trim();
			 if (lettura.length() > 0)
			  finito=true;
			 else
			   InputOutput.mostraMessaggio(Vista.INOUT_ERRORE_STRINGA_VUOTA);
		   } while (!finito);
		   
		   return lettura;
		  }
		  
		  public static char leggiChar (String messaggio)
		  {
		   boolean finito = false;
		   char valoreLetto = '\0';
		   do
		    {
			 InputOutput.mostraMessaggio(messaggio);
		     String lettura = lettore.next();
		     if (lettura.length() > 0)
		      {
		       valoreLetto = lettura.charAt(0);
		       finito = true;
		      }
		     else
		      {
		    	 InputOutput.mostraMessaggio(Vista.INOUT_ERRORE_STRINGA_VUOTA);
		      }
		    } while (!finito);
		   return valoreLetto;
		  }
		  
		  public static char leggiUpperChar (String messaggio, String ammissibili)
		  {
		   boolean finito = false;
		   char valoreLetto = '\0';
		   do
		   {
		    valoreLetto = leggiChar(messaggio);
		    valoreLetto = Character.toUpperCase(valoreLetto);
		    if (ammissibili.indexOf(valoreLetto) != -1)
			 finito  = true;
		    else
		    	InputOutput.mostraMessaggio(Vista.INOUT_MESSAGGIO_AMMISSIBILI + ammissibili);
		   } while (!finito);
		   return valoreLetto;
		  }
		  
		  
		  public static int leggiIntero (String messaggio)
		  {
		   boolean finito = false;
		   int valoreLetto = 0;
		   do
		    {
			   InputOutput.mostraMessaggio(messaggio);
		     if (lettore.hasNextInt())
		      {
		       valoreLetto = lettore.nextInt();
		       finito = true;
		      }
		     else
		      {
		    	 InputOutput.mostraMessaggio(Vista.INOUT_ERRORE_FORMATO);
		       String daButtare = lettore.next();
		      }
		    } while (!finito);
		   return valoreLetto;
		  }
	
		  public static int leggiInteroPositivo(String messaggio)
		  {
			  return leggiInteroConMinimo(messaggio,1);
		  }
		  
		  public static int leggiInteroNonNegativo(String messaggio)
		  {
			  return leggiInteroConMinimo(messaggio,0);
		  }
		  
		  
		  public static int leggiInteroConMinimo(String messaggio, int minimo)
		  {
		   boolean finito = false;
		   int valoreLetto = 0;
		   do
		    {
		     valoreLetto = leggiIntero(messaggio);
		     if (valoreLetto >= minimo)
		      finito = true;
		     else
		    	 InputOutput.mostraMessaggio(Vista.INOUT_ERRORE_MINIMO + minimo);
		    } while (!finito);
		     
		   return valoreLetto;
		  }
	
		  public static int leggiIntero(String messaggio, int minimo, int massimo)
		  {
		   boolean finito = false;
		   int valoreLetto = 0;
		   do
		    {
		     valoreLetto = leggiIntero(messaggio);
		     if (valoreLetto >= minimo && valoreLetto<= massimo)
		      finito = true;
		     else
		      if (valoreLetto < minimo)
		    	  InputOutput.mostraMessaggio(Vista.INOUT_ERRORE_MINIMO + minimo);
		      else
		    	  InputOutput.mostraMessaggio(Vista.INOUT_ERRORE_MASSIMO + massimo); 
		    } while (!finito);
		     
		   return valoreLetto;
		  }
	
		  
		  public static double leggiDouble (String messaggio)
		  {
		   boolean finito = false;
		   double valoreLetto = 0;
		   do
		    {
			   InputOutput.mostraMessaggio(messaggio);
		     if (lettore.hasNextDouble())
		      {
		       valoreLetto = lettore.nextDouble();
		       finito = true;
		      }
		     else
		      {
		    	 InputOutput.mostraMessaggio(Vista.INOUT_ERRORE_FORMATO);
		       String daButtare = lettore.next();
		      }
		    } while (!finito);
		   return valoreLetto;
		  }
		 
		  public static double leggiDoubleConMinimo (String messaggio, double minimo)
		  {
		   boolean finito = false;
		   double valoreLetto = 0;
		   do
		    {
		     valoreLetto = leggiDouble(messaggio);
		     if (valoreLetto >= minimo)
		      finito = true;
		     else
		    	 InputOutput.mostraMessaggio(Vista.INOUT_ERRORE_MINIMO + minimo);
		    } while (!finito);
		     
		   return valoreLetto;
		  }
	
		  
		  public static boolean yesOrNo(String messaggio)
		  {
			  String mioMessaggio = messaggio + "("+Vista.INOUT_RISPOSTA_SI+"/"+Vista.INOUT_RISPOSTA_NO+")";
			  char valoreLetto = leggiUpperChar(mioMessaggio,String.valueOf(Vista.INOUT_RISPOSTA_SI)+String.valueOf(Vista.INOUT_RISPOSTA_NO));
			  
			  if (valoreLetto == Vista.INOUT_RISPOSTA_SI)
				return true;
			  else
				return false;
		  }
		  
		  
		  public static void mostraMessaggio(String messaggio)
		  {
		        System.out.println(messaggio);
		  }
}
