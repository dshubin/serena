def props = new Properties();
final def inputPropsFile = args[0];
final def inputPropsStream = null;

try {
	inputPropsStream = new FileInputStream(inputPropsFile);
    	props.load(inputPropsStream);
} catch (IOException e) {
	throw new RuntimeException(e);
} finally {
	    inputPropsStream.close();
}
//required
final def name = props['createCookbookName'];
def sout = new StringBuffer();
def serr = new StringBuffer();

def crh = "";
def license = "";
def email = "";
def path = "";
def format = "";

//optional
if(props['createCookbookCopyrightHolder'] != "")
	crh = " -C " + props['createCookbookCopyrightHolder'];

if(props['createCookbookLicense'] != "")
	license = " -I " + props['createCookbookLicense'];

if(props['createCookbookEmail'] != "")
	email = " -m " + props['createCookbookEmail'];

if(props['createCookbookPath'] != "")
	path = " -o " + props['createCookbookPath'];

if(props['createCookbookFormat'] != "")
	format = " -r " + props['createCookbookFormat'];

final def options = "${crh} ${license} ${email} ${path} ${format}";

final def command = "knife cookbook create ${name} ${options}";

def proc = command.execute();
proc.consumeProcessOutput(sout, serr);
proc.waitFor();

println "sout: ${sout}";
println "serr: ${serr}";

System.exit(proc.exitValue());
