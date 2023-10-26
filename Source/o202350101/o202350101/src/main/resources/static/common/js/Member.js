Member = function(id, name, securityNo) {
	this.id = id;  
	this.name = name;
	this.securityNo = securityNo;
}
Member.prototype.setValue=function(newId,newName,newSecurityNo) {
	this.id = newId;
	this.name = newName;
	this.securityNo = newSecurityNo;
}
Member.prototype.getAge = function() {
	var birth = parseInt(this.securityNo.substring(0,2));
	var code = this.securityNo.substring(6,7);
	if (code == '1' || code == '2') {
		birth += 1900;
	} else birth += 2000;
	var date = new Date().getFullYear();
	return date - birth + 1;
}
Member.prototype.toString = function() {
	return this.name + "(" + this.id + ")";
}