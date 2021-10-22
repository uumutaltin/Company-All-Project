let globalCatArr = []
function fncDeleteProductCategory(){
    const cid = $("#pcategory").val();
    let answer = confirm("Silmek istediğinizden emin misiniz?");
    if ( answer ) {

        $.ajax({
            url: '/products/category/delete/'+cid,
            type: 'GET',
            dataType: 'text',
            success: function (data) {
                if ( data  == "true" ) {
                    alert("Silme İşlemi Başarılı");
                    fncCategoryList();
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
fncCategoryList();

function fncCategoryList(){
    $.ajax({
        url: '/products/category/list',
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
let selecthtml =``;
let pcat = $("#pcategory");
function createCategory( data ){
    pcat.find('option').remove().end();
   pcat.append(` <option value="-1" >Kategori Seçiniz</option>`);
    globalCatArr = data;
    for (let i = 0; i<data.length;i++){
        const itm = data[i];
        const st = itm.pcategoriesname
        console.log(st);
        console.log(itm.pcategory.length);
        pcat.append(`<option data-subtext="" value="`+itm.pcsid+`">`+st+`</option>` );
        if (itm.pcategory.length > 0 ){
            for (let j = 0; j<itm.pcategory.length;j++){
                const cat = itm.pcategory[j]
                fncSpaceControl(cat)
            }
        }
    }
    pcat.selectpicker("refresh");
}

function fncSpaceControl(data){
    console.log(data);
    const st = data.pcategoryname;
    pcat.append(`<option data-subtext="" value="`+data.pcid+`">&nbsp&nbsp&nbsp&nbsp`+st+`</option>` );
}

function fncUpdateProductCategory(){
    let pcatval = $("#pcategory").val();
    let pcatname = $("#pcategoryname").val();
    const obj ={
        pcid:pcatval,
        pcategoryname: pcatname
    }
    if(pcatval != -1){
        $.ajax({
            url: '/products/category/update',
            type: 'POST',
            data: JSON.stringify(obj),
            dataType: 'JSON',
            contentType: 'application/json; charset=utf-8',
            success: function (data) {
                if ( data  == true ) {
                    alert("Güncelleme İşlemi Başarılı");
                    fncCategoryList();
                    $("#pcategoryname").val("");
                }else {
                    alert("Güncelleme sırasında bir hata oluştu!");
                }
            },
            error: function (err) {
                console.log(err)
            }
        })
    }
}