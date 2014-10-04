with Ada.Integer_Text_IO, Ada.Text_IO, Ada.Float_Text_IO;
use Ada.Integer_Text_IO, Ada.Text_IO, Ada.Float_Text_IO;

procedure Matriz is

--variables
	type Matrix is array (1 .. 2, 1 .. 5) of Integer;--Matriz de 2x5
	type Vector is array (1 .. 5) of Float;
	A : Matrix;
	B : Vector;
	Num : Float := 0.0;
begin
	A := ((14, 12, 17, 24, 28),(3, 2, 4, 2, 5));
	for i in 1 .. B'length
	loop
		Num := Float(A(1, i))/Float(A(2, i));
		B(i) := Num;
	end loop;
	for i in 1 .. B'length
	loop
		Put(B(i), EXP => 0, AFT => 3);
		New_Line;
	end loop;

end Matriz;
