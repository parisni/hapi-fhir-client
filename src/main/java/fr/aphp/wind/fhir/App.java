package fr.aphp.wind.fhir;

import org.hl7.fhir.dstu3.model.Bundle;
import org.hl7.fhir.dstu3.model.Patient;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.rest.client.IGenericClient;
/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	FhirContext ctx = FhirContext.forDstu3();    
    	// Working with RI structures is similar to how it works with the HAPI structures
    	org.hl7.fhir.dstu3.model.Patient patient = new org.hl7.fhir.dstu3.model.Patient();
    	patient.addName().addGiven("John").setFamily("Smith");
    	patient.getBirthDateElement().setValueAsString("1998-02-22");
    	 
    	// Parsing and encoding works the same way too
    	String encoded = ctx.newJsonParser().encodeResourceToString(patient);
    	
        System.out.println( encoded );
        
        String serverBase = "http://fhirtest.uhn.ca/baseDstu3";
        
        IGenericClient client = ctx.newRestfulGenericClient(serverBase);
        Bundle results = client
        	      .search()
        	      .forResource(Patient.class)
        	      .where(Patient.FAMILY.matches().value("duck"))
        	      .returnBundle(Bundle.class)
        	      .execute();
        System.out.println( ctx.newJsonParser().encodeResourceToString(results));
    }
}
