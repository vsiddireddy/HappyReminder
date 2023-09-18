
$(document).ready(function() {
   // Capture form submission event
	$("form").submit(function(event) {
       	// Prevent the default form submission behavior
		event.preventDefault();
		console.log("hello!")
	    const urlParams = new URLSearchParams(window.location.search);
	
	    // Add the query parameter
	    urlParams.set('personName', document.getElementById("personName").value); // Replace 'parameterName' with your parameter name
		urlParams.set('birthDate', document.getElementById("birthDate").value);
		urlParams.set('relation', document.getElementById("relation").value);
		urlParams.set('personName', document.getElementById("personName").value);
		urlParams.set('reminderDate', document.getElementById("reminderDate").value);
		urlParams.set('timezone', document.getElementById("timezone").value);
		
	    // Get the updated URL with the query parameter
	    const updatedUrl = `${window.location.pathname}?${urlParams.toString()}`;
	    
	    window.location.href = updatedUrl;

       // Optionally, you can return false to prevent further event propagation
       // and prevent the form from submitting traditionally.
       return false;
   });
});

let url = window.location.href;
if(url.includes('?')){
	console.log('Parameterised URL');
	var spn = document.getElementById("SpringPersonName").outerText;
	var obj = document.getElementById("SpringObj");
	console.log(obj.innerText);
	var arr = obj.innerText.replace(/\[|\]/g,'').split(',');
	console.log(arr);
	
	var div = document.createElement('div');
	for (var x = 0; x < arr.length; x++) {
		
		if(arr[x].includes("{")) {
			div = document.createElement('div');
		}
		
		var split = arr[x].split('=');
		
		if (split[1].includes("}")) {
			var remove = split[1].split("}")[0];
			div.innerHTML += '<h1>' + remove + '</h1>';
			div.innerHTML += "<button onClick='deletePerson()'>X</button>";
			document.getElementById('boxContainer').appendChild(div);
		} else {
			div.innerHTML += '<h1>' + split[1] + '</h1>';
		}
	}
} else {
	console.log('No Parameters in URL');
}


function deletePerson() {
	if(window.confirm("Are you sure want to delete this?")) {
		
	}

}




