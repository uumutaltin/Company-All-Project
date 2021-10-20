let globalCatArr = []

fncCategoryList();

function fncCategoryList(){
    $.ajax({
        url: '/news/category/list',
        type: 'GET',
        success: function (data) {
            console.log(data)
            createCategory(data)
        },
        error: function (err){
            console.log(err)
        }
    })
}


let ncat = $("#nclist");
function createCategory( data ){
    ncat.find('option').remove().end();
    ncat.append(` <option value="-1" >Kategori Seçiniz</option>`);
    globalCatArr = data;
    for (let i = 0; i<data.length;i++){
        const itm = data[i];
        const st = itm.nctitle

        ncat.append(`<option data-subtext="" value="`+itm.ncid+`">`+st+`</option>`)
    }
    ncat.selectpicker("refresh");
}