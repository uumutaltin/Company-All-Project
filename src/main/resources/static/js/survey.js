let globalArr = []
let pageNumber = 0

function changeVariables(dataNumber){
    if (dataNumber == -5) {
        dataNumber = lastPageNumber-1;

    }
    pageNumber = dataNumber;
    const asearch = $("#search").val()
    if( asearch != "") {
        fncSearch();
    }
    else{
        allAd()
    }
}
allAd()

function allAd( ){
    const pageSize = $("#cPage").val()
    pageCount(1);
    $.ajax({
        url: './survey/surveyList/'+pageNumber+'/'+pageSize,
        type: 'GET',
        contentType: 'application/json; charset=utf-8',
        success: function (data) {
            console.log(data)
            createRow(data)
        },
        error: function (err){
            console.log(err)
        }
    })
}

function createRow( data ) {
    globalArr = data;
    let html = ``
    for (let i = 0; i < data.length; i++) {
        const itm = data [i];
        html += `<tr>
          <td style="text-align: center">`+itm.stitle +`</td>
          <td style="text-align: center" >
            <div class="btn-group" role="group" aria-label="Basic mixed styles example">
               <button onclick="fncSurveyDelete('`+itm.said+`')"  type="button" class="btn btn-outline-danger "><i class="far fa-trash-alt"></i></button>
              <a href="/survey/detail/`+itm.said+`"><button  type="button" class="btn btn-outline-primary "><i class="fas fa-pencil-alt"></i></button></a>
            </div>
          </td>
        </tr>`;
    }
    $('#surveyRow').html(html);
}


function pagePlus(){
    const pageSize = $("#cPage").val()
    let plusNumber = globalArr.length
    let pageNumberx = pageNumber
    if( plusNumber < pageSize ){
        pageNumber = pageNumberx
    }
    else{
        pageNumber++
    }

    console.log('GlobalArr Length : '+plusNumber)
    /*    lastPage()*/
    console.log(pageNumber)
    const asearch = $("#search").val()
    if( asearch != "") {
        fncSearch();
    }
    else{
        allAd()
    }
}
function pageMinus(){
    console.log('GlobalArr Length : '+globalArr.length)
    if(pageNumber <= 0){
        pageNumber=0
    }else {
        pageNumber--
    }
    /*    lastPage()*/
    console.log(pageNumber)
    const asearch = $("#search").val()
    if( asearch != "") {
        fncSearch();
    }
    else{
        allAd()
    }

}

function fncSurveyDelete( said ) {
    let answer = confirm("Silmek istediğinizden emin misiniz?");
    if ( answer ) {

        $.ajax({
            url: '/survey/delete/'+said,
            type: 'GET',
            dataType: 'text',
            success: function (data) {
                if ( data != "0" ) {
                    allAd();
                }else {
                    alert("Silme sırasında bir hata oluştu!");
                }
            },
            error: function (err) {
                console.log(err)
            }
        })
    }
}

function fncSearch() {
    const pageSize = $("#cPage").val()
    const asearch = $("#search").val()
    if( asearch != "") {
        $.ajax({
            url: '/survey/search/'+pageNumber+'/'+pageSize +'/'+asearch,
            type: 'GET',
            contentType: 'application/json; charset=utf-8',
            success: function (data) {
                console.log(data)
                pageCount(2)
                createRow(data)
            },
            error: function (err) {
                console.log(err)
            }
        })
    }
    else {
        allAd()
    }
}

pageCount(1)
function pageCount(countStatus){
    const pageSize = $("#cPage").val()
    $.ajax({
        url: './survey/surveyList/pageCount/'+pageSize+'/'+countStatus,
        type: 'GET',
        contentType: 'application/json; charset=utf-8',
        success: function (data) {
            console.log(data)
            $("#totalPageNumber").text(pageNumber+1 + '/' + data)
            lastPageNumber = data;
        },
        error: function (err){
            console.log(err)
        }
    })
}

