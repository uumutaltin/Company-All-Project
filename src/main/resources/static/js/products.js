$(".chosen-select").chosen({
    no_results_text: "Oops, nothing found!"
})
var chosen = $(".chosen-select").val();
console.log(chosen)

function changed(){
    var chosen = $(".chosen-select").val();
    console.log(chosen)
}