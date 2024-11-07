<?php
namespace Backend;
abstract class DataBase {
    protected $conexion;

    public function __construct($user, $password, $dbName) {
        $host = 'localhost';
        $this->conexion = @mysqli_connect($host, $user, $password, $dbName);
        if (!$this->conexion) {
            die('No hay conexion');
        }
    }

    abstract protected function query($sql);

    public function __destruct() {
        if ($this->conexion) {
            mysqli_close($this->conexion);
        }
    }
}
?>