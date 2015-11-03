${rootNode.employeeName}
${rootNode.age}
${rootNode.sex}
${rootNode.MaritalStatues}
<#list rootNode.Adresses as address>
	${address.Address1}
	${address.Address2}
	${address.Address3}
	${address.Address4}
	${address.State.Code}
	${address.State.Name}
</#list>
<#list rootNode.PhoneList as phone>
	${phone.PhoneType} - ${phone.PhoneNumber}
</#list>
<#list rootNode.InsuredNames as InsuredName>
	${InsuredName.Decription}
	<#list InsuredName.Names as Name>
		${Name}
	</#list>
</#list>