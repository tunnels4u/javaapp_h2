package com.softwaretunnel.javaapp_h2;

import com.softwaretunnel.javaapp_h2.persistance.H2Interaction;

/**
 * Hello world!
 *
 */
public class App 
{
	private static H2Interaction h2Interaction = H2Interaction.getH2Interaction();
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        h2Interaction.createDatabase();
        h2Interaction.createSchema();
    }
}
