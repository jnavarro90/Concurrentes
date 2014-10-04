with Ada.Integer_Text_IO, Ada.Text_IO, Ada.Float_Text_IO;
use Ada.Integer_Text_IO, Ada.Text_IO, Ada.Float_Text_IO;

procedure VectorReales is

--variables
	type Reales is digits 3 range 0.0 .. 50.0;
	type Vector is array (1 .. 10) of Reales;
	V : Vector;
	Num : Float := 0.0;
	Opcion : Integer;
begin
	V := (1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0);

--Menu
	Put_Line("-----Menu-----");
	Put_Line("1. Mostrar cada posicion del vector.");
	Put_Line("2. Multiplicar por 2 cada posicion y mostrarla.");
	Put_Line("3. Calcular la suma de todas las posiciones del vector.");
	Put_Line("Introduce una opcion:");
	Get(Opcion);

--control para repita el pedir un numero hasta q sea correcto
	while Opcion < 1 or Opcion > 3
	loop	
		Put_Line("Introduce una opcion correcta:");
		Get(Opcion);
	end loop;
		case Opcion is 
			when 1 =>
				for i in 1..V'length
				loop
					Put(Float(V(i)), EXP=>0, AFT => 2);
					New_Line;
				end loop;
			when 2 =>
				for i in 1..V'length
				loop
					Put(Float(V(i)*2.0), EXP=>0, AFT => 2);
					New_Line;
				end loop;
			when 3 => 
				for i in 1..V'length
				loop
					Num := Num + Float(V(i));
				end loop;
				Put(Num, EXP=>0, AFT => 2);
				New_Line;
			when others =>
				Put("Error");
		end case;
end VectorReales;
