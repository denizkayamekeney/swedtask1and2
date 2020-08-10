    function cascoCalculation(node){
       var dt = $("#casco-form").serialize();

            $.ajax({
                type : "POST",
                url : "/vehicles/casco/ajax/",
                data : dt,
                success : function(response) {
                   $("#resultTable").html(response);
                },
                error : function(e) {
                   alert('Failed!: ' + e);
                }
            });
    }
