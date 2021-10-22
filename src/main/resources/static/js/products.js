let pageNumber = 0
let globalArr = []
let lastPageNumber = 0;
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
        allProducts
    }
}
allProducts();
function allProducts( ){
    const pageSize = $("#cPage").val()
    pageCount(1);
    /*    lastPage()*/
    $.ajax({
        url: './products/productList/'+pageNumber+'/'+pageSize,
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

function pagePlus(){
    const pageSize = $("#cPage").val()
    let plusNumber = globalArr.length
    let pageNumberx = pageNumber
    console.log('plusNumber : ' + plusNumber)
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
        allProducts()
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
        allProducts()
    }

}

function createRow( data ) {
    globalArr = data;
    let html = ``
    for (let i = 0; i < data.length; i++) {
        const itm = data [i];
        let tempcat = "";
        for (let j = 0 ; j<itm.pcategories.length; j++){
            tempcat += itm.pcategories[j].pcategoryname+',';
        }
        const category = tempcat;
        html += `                <tr>
                    <td class="text-center">`+itm.pname+`</td>
                    <td class="text-center">`+itm.psdetail+`</td>
                    <td class="text-center">`+itm.pdetail+`</td>
                    <td class="text-center">`+category+`</td>
                    <td class="text-center">
                        <div class="btn-group" role="group" aria-label="Basic mixed styles example">
                            <button onclick="fncProductDelete(`+itm.pid+`)"  type="button" class="btn btn-outline-primary "><i class="far fa-trash-alt"></i></button>
                        <a href="/products/update/`+itm.pid+`">    <button  type="button" class="btn btn-outline-primary "><i class="fas fa-pencil-alt"></i></button></a>
                        </div>
                    </td>
                </tr>`;
    }
    $('#contentRow').html(html);
}

pageCount(1)
function pageCount(countStatus){
    const pageSize = $("#cPage").val()
    $.ajax({
        url: './news/newsList/pageCount/'+pageSize+'/'+countStatus,
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
function fncProductDelete( id ) {
    let answer = confirm("Silmek istediğinizden emin misiniz?");
    if ( answer ) {

        $.ajax({
            url: '/products/delete/'+id,
            type: 'GET',
            dataType: 'text',
            success: function (data) {
                if ( data != "0" ) {
                    allProducts();
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
            url: '/products/search/'+pageNumber+'/'+pageSize +'/'+asearch,
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
        allProducts()
    }
}
