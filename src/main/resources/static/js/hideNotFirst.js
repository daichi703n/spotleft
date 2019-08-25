function hideNotFirst() {
  const notFirst = document.getElementsByClassName('notFirst');

	if(notFirst[0].style.display!="none"){
    for(i=0;i<notFirst.length;i++){
      notFirst[i].style.display = "none";
    }
	}else{
    for(i=0;i<notFirst.length;i++){
      notFirst[i].style.display = null;
    }
	}
}
