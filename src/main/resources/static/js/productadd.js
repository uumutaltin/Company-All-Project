$("#pcategory").chosen({
    no_results_text: "Oops, nothing found!"
})
var chosen = $("#pcategory").val();
console.log(chosen)

function changed(){
    var chosen = $("#pcategory").val();
    console.log(chosen)
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
/*
let pcat = $('#pcategory')
function createCategory( data ){
    pcat.find('option').remove().end();

    globalCatArr = data;
    for (let i = 0; i<data.length;i++){
        const itm = data[i];
        const st = itm.pcategoriesname
        console.log("Burada")
        pcat.append(`<option data-subtext="" value="`+itm.pcsid+`">`+st+`</option>` );

    }
}*/
