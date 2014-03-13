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
    	
// @param deployCommand = knife ssh "nodeName" "sudo chef-client"
	
// name of the node to deploy on
final def query = props["target"];
final def forwarding = props["forwarding"]?.toBoolean()
final def proxyUrl = props["proxyUrl"]?:''
final def version = props["version"]?:''
final def distro = props["distro"]?:''
final def gateway = props["gateway"]?:''
final def hint = props["hint"]?:''
final def identity = props["identity"]?:''
final def jsonAttr = props["jsonAttr"]?:''
final def nodeName = props["name"]?:''
final def hostKeyVerify = props["hostKeyVerify"]?.toBoolean()
final def port = props["port"]?:''
final def password = props["password"]?:''
final def prerelease = props["prerelease"]?.toBoolean()
final def runList = props["runList"]?:''
final def secret = props["secret"]?:''
final def secretFile = props["secretFile"]?:''
final def sudo = props["sudo"]?.toBoolean()
final def sudoPassword = props["sudoPassword"]?:''
final def myTemplate = props["template"]?:''
final def useSudoPassword = props["useSudoPassword"]?.toBoolean()
final def user = props["user"]?:''
// command to be executed
def deployCommand = "knife bootstrap ${target}";
if(forwarding){
	deployCommnad = deployCommand + " -A"
}
if(proxyUrl){
	deployCommnad = deployCommand + " --bootstrap-proxy ${proxyUrl}"
}
if(version){
	deployCommand = deployCommand + " --bootstrap-version ${version}"
}
if(distro){
	deployCommand = deployCommand + " -d ${distro}"
}
if(gateway){
	deployCommand = deployCommand + " -G ${gateway}"
}
if(hint){
	deployCommand = deployCommand + " --hit ${hint}"
}
if(identity){
	deployCommand = deployCommand + " -i ${identity}"
}
if(jsonAttr){
	deployCommand = deployCommand + " -j ${jsonAttr}"
}
if(nodeName){
	deployCommand = deployCommand + " -N ${nodename}"
}
if(!hostkeyVerify){
	deployCommand = deployCommand + " --no-host-key-verify"
}
if(port){
	deployCommand = deployCommand + " -p ${port}"
}
if(password){
	deployCommand = deployCommand + " -P ${password}"
}
if(prerelease){
	deployCommand = deployCommand + " --prerelease"
}
if(runList){
	deployCommand = deployCommand + " -r ${runList}"
}
if(secret){
	deployCommand = deployCommand + " --secret ${secret}"
}
if(secretFile){
	deployCommand = deployCommand + " --secret-file ${secretFile}"
}
if(sudo){
	deployCommand = deployCommand + " --sudo"
}
if(myTemplate){
	deployCommand = deployCommand + " --template-file ${myTemplate}"
}
if(useSudoPassword){
	deployCommand = deployCommand + " --use-sudo-password"
}
if(user){
	deployCommand = deployCommand + " -x ${user}"
}

def sout = new StringBuffer();
def serr = new StringBuffer();
def proc = deployCommand.execute();
proc.consumeProcessOutput(sout, serr);
if(sudo && !useSudoPassword){
	proc.withWriter { writer ->
		writer << sudoPassword
	}
}
proc.waitFor();

println "sout: ${sout}"
println "serr: ${serr}"

System.exit(0);
    
