<?php
    use ProductApp\Backend\MyApi\Products;
    require_once __DIR__.'/../myapi/Products.php';
    $productos = new Products('marketzone');
    $productos->delete( $_POST['id'] );
    echo $productos->getData();
?>