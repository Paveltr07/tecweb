<?php
namespace Backend;
include_once __DIR__.'/Products.php';
$product = new Products();
$product->search($_GET['search']);
echo $product->getData();
?>