/* Title-Footer object and properties declaration with default values */

var title_footer=
{
	title: '',
	speaker: '',
	company: ''
};

/* Function to obtain all child elements with any of the indicated tags (from http://www.quirksmode.org/dom/getElementsByTagNames.html) */

function getElementsByTagNames(list,obj)
{
	if (!obj)
	{
		var obj=document;
	};
	var tagNames=list.split(',');
	var resultArray=new Array();
	for (var i=0;i<tagNames.length;i++)
	{
		var tags=obj.getElementsByTagName(tagNames[i]);
		for (var j=0;j<tags.length;j++)
		{
			resultArray.push(tags[j]);
		};
	};
	var testNode=resultArray[0];
	if (!testNode)
	{
		return [];
	};
	if (testNode.sourceIndex)
	{
		resultArray.sort(
			function (a,b)
			{
				return a.sourceIndex-b.sourceIndex;
			}
		);
	}
	else if (testNode.compareDocumentPosition)
	{
		resultArray.sort(
			function (a,b)
			{
				return 3-(a.compareDocumentPosition(b)&6);
			}
		);
	};
	return resultArray;
};

function moveSlideTitle() {
    var elements=document.getElementsByTagName('section');
    var presentSection;
    for (var i=0;i<elements.length;i++) {
    	var element = elements[i];
    	if (element.classList.contains('present')) {
            presentSection = element;
            i = elements.length+1;
		}
    }

    if (presentSection.getAttribute("data-state") == "no-title-footer") {
    	return;
	}

    var title_elements=this.getElementsByTagNames('h1,h2,h3',presentSection);
    var title;
    if (title_elements.length>0) {
        title=title_elements[0];
    }

    var slideTitle = document.getElementsByClassName("header header-left slide-title")[0];
    if (title && slideTitle) {
        while (slideTitle.hasChildNodes()) {
            slideTitle.removeChild(slideTitle.lastChild);
        }
        slideTitle.appendChild(document.createTextNode(title.textContent))
		title.setAttribute("style", "display: none");
    }
};

function initHeader() {
    var title_header=document.createElement('header');
    title_header.setAttribute('id','title-header');
    var title_header_p=document.createElement('div');
    title_header_p.setAttribute('style','width: 100%');    

    var div_slide_title = document.createElement('div');
    div_slide_title.setAttribute('class','header header-left slide-title');

    var div_company = document.createElement('div');
    div_company.setAttribute('class','header header-right');

    var div_company_image = document.createElement('img');
    div_company_image.setAttribute('src', 'images/anderScore/anderScore_green.png');
    div_company.appendChild(div_company_image)

    title_header.appendChild(div_slide_title);
    title_header.appendChild(div_company);


    var div_class_reveal=document.querySelectorAll('.reveal')[0];
    div_class_reveal.appendChild(title_header);
}

function initFooter() {
    // Create the Title-Footer footer

    var title_footer=document.createElement('footer');
    title_footer.setAttribute('id','title-footer');  
   
    var div_element_left = document.createElement('div');
    div_element_left.setAttribute('class','footer footer-left');
    div_element_left.appendChild(document.createTextNode(this.title_footer.company + " | " + this.title_footer.speaker));       

    
    //Date today
    var date = new Date();
    var day = date.getDate();
    if (day < 10) {
    	day = '0' + day;
    }
    var month = date.getMonth()+1
    if (month < 10) {
        month = '0' + month;
    }
    var div_element_middle=document.createElement('div');
    div_element_middle.setAttribute('class','footer footer-middle');
    div_element_middle.appendChild(document.createTextNode(this.title_footer.title + " | "));    
    div_element_middle.appendChild(document.createTextNode(day + '.' + month + '.' + date.getFullYear()));
    
    var div_element_right=document.createElement('div');
    div_element_right.setAttribute('class','footer footer-right');
    var slideNumber = document.getElementsByClassName("slide-number");
    if (slideNumber.length>0) {
    	slideNumber = slideNumber[0];
        div_element_right.appendChild(slideNumber);
	}



    title_footer.appendChild(div_element_left);
    title_footer.appendChild(div_element_middle);
    title_footer.appendChild(div_element_right);

    var div_class_reveal=document.querySelectorAll('.reveal')[0];
    div_class_reveal.appendChild(title_footer);
}


/* Method to initialize the Title-Footer footer */

title_footer.initialize=function(title,speaker, company)
{

	// Link to the Title-Footer CSS

	var link=document.createElement("link");
	link.href="plugin/title-footer/title-footer.css";
	link.type="text/css";
	link.rel="stylesheet";
	document.getElementsByTagName("head")[0].appendChild(link);

	// Initialize properties according to parameters

    this.title=title || '';
    this.speaker=speaker || '';
    this.company=company || '';

    initHeader();
    initFooter();
    moveSlideTitle();

    Reveal.addEventListener( 'slidechanged', function( event ) {
        moveSlideTitle();
    } );
};

