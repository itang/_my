let multiply n list = 
  let f x = 
    n * x
  in
  List.map f list;;

multiply 2 [1; 2; 3];;

(* Partial function applications and currying *)
let plus a b =
  a + b
;;
let f = plus 2;;
assert (f 10 == 12);;

let double = multiply 2;;
let triple = multiply 3;;
assert(double [1;2;3] = [2;4;6]);;

let ret = List.map (( * ) 2) [1;2;3];;
assert(ret = [2;4;6]);;

(* Strictness vs laziness *)
let lazy_expr = lazy (1/0);;
print_string "here";;
(* Lazy.force lazy_expr;; *)

(* Boxed vs. unboxed types *)



