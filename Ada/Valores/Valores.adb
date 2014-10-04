with Ada.Integer_Text_IO, Ada.Text_IO;
use Ada.Integer_Text_IO, Ada.Text_IO;

procedure Valores is

--variables
	numero : Integer;


--Función para saber si un numero es par 
function esPar(numero : Integer) return Boolean is 
begin
	if (numero mod 2) = 0 then
		return true;
	else
		return false;
	end if;
end esPar;

begin
	Put_Line("Introduce un numero");
	Get(numero);
	for i in 1..numero
	loop
		Put(i);
		New_Line;
	end loop;
	if esPar(numero) then
		Put("Es par");
	else
		Put("Es impar");
	end if;
end Valores;
