package tests.normalmap.shader;

public class TnLShader {

    public static final String mVertexShader =
    	"attribute vec4 a_Position;		\n" +
    	"attribute vec3 a_Normal;		\n"+
    	"uniform mat4 MVPMatrix;			\n"+
    	"uniform vec3 LightDirection;		\n"+
    	"varying vec3 DiffuseLight;	\n"+
    	"varying vec3 SpecularLight;	\n"+
    	"void main() {						\n"+
    	"	gl_Position = MVPMatrix * inVertex;\n"+
    	"	DiffuseLight = vec3(max(dot(inNormal, LightDirection), 0.0));\n"+
    	"	SpecularLight = vec3(max((DiffuseLight.x - MaterialBias) * MaterialScale, 0.0));\n"+
    	"}\n";

    public static final String mFragmentShader =   
        "#ifdef GL_ES\n" +
        "precision mediump float;\n" +
        "#endif\n" +
    	"varying vec3 DiffuseLight;	\n"+
    	"varying vec3 SpecularLight;	\n"+
    	"void main() {						\n"+
    	"vec3 color = DiffuseLight + SpecularLight;\n"+
    	"gl_FragColor = vec4(color, 1.0);	\n"+
    	"}\n";
}
