package service.uml;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import service.FileService;

import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UmlServiceTest {
    private static UmlService umlService;
    private static Path path;

    @BeforeAll
    public static void setUp() {
        umlService = new UmlService();
        FileService fileService = new FileService();
        path = fileService.getPath("/input");
    }

    @Test
    void testGetClassesForPath(){
        //given
        List<Class> classes = umlService.getClassesForPath(path);

        //then
        assertEquals(9,classes.size());
        assertEquals("input.Antique",classes.get(0).getCanonicalName());
        assertEquals("input.AntiqueBuyableFahrrad",classes.get(1).getCanonicalName());
        assertEquals("input.Auto",classes.get(2).getCanonicalName());
        assertEquals("input.Buyable",classes.get(3).getCanonicalName());
        assertEquals("input.Cabriolet",classes.get(4).getCanonicalName());
        assertEquals("input.Fahrrad",classes.get(5).getCanonicalName());
        assertEquals("input.Fahrzeug",classes.get(6).getCanonicalName());
        assertEquals("input.Item",classes.get(7).getCanonicalName());
        assertEquals("input.Person",classes.get(8).getCanonicalName());
    }

    @Test
    void testGlassToClassDTO(){
        //given
        ClassDTO classDTOAntique = umlService.getClassDTOs().get(0);
        //then
        assertEquals("input.Antique",classDTOAntique.getFullClassName());
        assertEquals("Antique",classDTOAntique.getSimpleClassName());
        assertTrue(classDTOAntique.isInterface());
        assertEquals(0,classDTOAntique.getImplementedInterfaces().size());
        assertNull(classDTOAntique.getSuperClassName());

        //given
        ClassDTO classDTOAntiqueBuyableFahrrad = umlService.getClassDTOs().get(1);
        //then
        assertEquals("AntiqueBuyableFahrrad",classDTOAntiqueBuyableFahrrad.getSimpleClassName());
        assertFalse(classDTOAntiqueBuyableFahrrad.isInterface());
        assertEquals(2,classDTOAntiqueBuyableFahrrad.getImplementedInterfaces().size());
        assertEquals("input.Antique",classDTOAntiqueBuyableFahrrad.getImplementedInterfaces().get(0));
        assertEquals("input.Buyable",classDTOAntiqueBuyableFahrrad.getImplementedInterfaces().get(1));
        assertEquals("input.Fahrrad",classDTOAntiqueBuyableFahrrad.getSuperClassName());

        //given
        ClassDTO classDTOFahrrad = umlService.getClassDTOs().get(5);
        //then
        assertEquals("Fahrrad",classDTOFahrrad.getSimpleClassName());
        assertFalse(classDTOFahrrad.isInterface());
        assertEquals("input.Fahrzeug",classDTOFahrrad.getSuperClassName());
    }

    @Test
    void testClassToClassDTOFields(){
        ClassDTO fahrzeug = umlService.getClassDTOs().get(6);

        assertEquals("private",fahrzeug.getFields().get(0).getAccessType());
        assertEquals("double",fahrzeug.getFields().get(0).getType());
        assertEquals("speed",fahrzeug.getFields().get(0).getName());

        assertEquals("package",fahrzeug.getFields().get(1).getAccessType());
        assertEquals("String",fahrzeug.getFields().get(1).getType());
        assertEquals("name",fahrzeug.getFields().get(1).getName());
    }

    @Test
    void testClassToClassDTOConstructors(){
        ClassDTO person = umlService.getClassDTOs().get(8);

        assertEquals("Person",person.getConstructors().get(0).getName());

        assertEquals("public",person.getConstructors().get(0).getAccessType());
        assertEquals(0,person.getConstructors().get(0).getInputParameters().size());

        assertEquals("package",person.getConstructors().get(1).getAccessType());
        assertEquals(1,person.getConstructors().get(1).getInputParameters().size());

        assertEquals("private",person.getConstructors().get(2).getAccessType());
        assertEquals(2,person.getConstructors().get(2).getInputParameters().size());

    }

    @Test
    public void testGetEdgeDTOs(){
        //given
        List<EdgeDTO> edgeDTOs =  umlService.getEdgeDTOs();
        //then
        assertNotNull(edgeDTOs);
        assertEquals(7, edgeDTOs.size());
        assertEquals("AntiqueBuyableFahrrad",edgeDTOs.get(0).getSource());
        assertEquals("Fahrrad",edgeDTOs.get(0).getTarget());
        assertEquals("extends",edgeDTOs.get(0).getType());
    }


}