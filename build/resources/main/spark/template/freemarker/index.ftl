<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>Practica3</title>
    <link rel="stylesheet" href="assets/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Montserrat:400,400i,700,700i,600,600i">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="assets/css/Article-List.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/baguettebox.js/1.10.0/baguetteBox.min.css">
    <link rel="stylesheet" href="assets/css/smoothproducts.css">
    <link rel="stylesheet" href="assets/css/Footer-Clean.css">
</head>
<body>

<nav class="navbar navbar-light navbar-expand-lg fixed-top bg-white clean-navbar">
    <div class="container"><a class="navbar-brand logo" href="/index">Practica 3</a><button class="navbar-toggler" data-toggle="collapse" data-target="#navcol-1"><span class="sr-only">Toggle navigation</span><span class="navbar-toggler-icon"></span></button>
        <div class="collapse navbar-collapse"
             id="navcol-1">
            <ul class="nav navbar-nav ml-auto">
                <li class="nav-item" role="presentation"><a class="nav-link active" href="/index">Inicio</a></li>
                <#if usuario?has_content>
                    <#if usuario.administrador == true>
                        <li class="nav-item" role="presentation"><a class="nav-link" href="/articulo">Articulos</a></li>
                        <li class="nav-item" role="presentation"><a class="nav-link" href="/user">Usuarios</a></li>
                        <li class="nav-item" role="presentation"><a class="nav-link" href="/crear">Crear</a></li>
                    <#elseif usuario.autor == true>
                        <li class="nav-item" role="presentation"><a class="nav-link" href="/articulo">Articulos</a></li>
                        <li class="nav-item" role="presentation"><a class="nav-link" href="/crear">Crear</a></li>
                    </#if>
                </#if>
                <li class="nav-item" role="presentation"><a class="nav-link" href="/salir">Salir</a></li>
            </ul>
        </div>
    </div>
</nav>
<div class="table-responsive">
    <table class="table">
        <thead>
        <tr>
            <th>Column 1</th>
            <th>Column 2</th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td>Cell 1</td>
            <td>Cell 2</td>
        </tr>
        <tr></tr>
        </tbody>
    </table>
</div>
<div class="article-list">
    <div class="container">
        <div class="intro">
            <h2 class="text-center">Ultimos articulos</h2>
        </div>
        <div class="cont">
            <div class="row articles pagination-container">
                <#if articulos?has_content>
                    <#list articulos as articulo>
                        <div class="col-sm-6 col-md-4 item" data-page="${articulo?index+1}"><a href="#"></a>
                            <h3 class="name">${articulo.titulo}</h3>
                            <p class="description">${articulo.cuerpo[0..70]}</p><a class="action" href="/post?id_post=${articulo.id}"><i class="fa fa-arrow-circle-right"></i></a></div>
                    </#list>
                </#if>
                <div class="pagination pagination-centered">
                    <ul class="pagination">
                        <li data-page="-" class="page-item"><a class="page-link" href="#" aria-label="Previous"><span aria-hidden="true">&lt;</span></a></li>
                        <li data-page="1" class="page-item"><a class="page-link" href="#">1</a></li>
                        <li data-page="2" class="page-item"><a class="page-link" href="#">2</a></li>
                        <li data-page="3" class="page-item"><a class="page-link" href="#">3</a></li>
                        <li data-page="4" class="page-item"><a class="page-link" href="#">4</a></li>
                        <li data-page="5" class="page-item"><a class="page-link" href="#">5</a></li>
                        <li data-page="+" class="page-item"><a class="page-link" href="#" aria-label="Next"><span aria-hidden="true">&gt;</span></a></li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="footer-clean">
    <footer>
    </footer>
</div>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.1.3/js/bootstrap.bundle.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/baguettebox.js/1.10.0/baguetteBox.min.js"></script>
<script src="assets/js/smoothproducts.min.js"></script>
<script src="assets/js/theme.js"></script>
<script src="js/pagination.js"></script>
</body>
</html>