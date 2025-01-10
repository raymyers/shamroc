# Shamroc

> The Shamroc metacompiler

Experimental, obviously.


Shamroc will enable rapid prototyping in language design by generating 
data structures for multiple layers of intermediate representations.

## Why?

So you want Antlr and an AST? Well of course, everyone does.

But more specifically you want a series of ASTs because you are
following the Nanopass compiler pattern.

You want these AST to be typesafe, and it should be easy to define each
as minor changes to the previous.

Naturally then you've concluded that you want ADTs (Algebraic Data Types),
with the wonky exception that they can inherit from each-other in
non-traditional ways. If you've gotten this far you already know that this is
hard to achieve, because that is an unusual thing to want. It's very common
in language tooling but not so much elsewhere.

So if you want to do many passes of tree transformation, you've got these options:

1) Express your trees with lots of duplication
2) Give up type-safety
3) Use new language with an innovative type system
4) **Use code gen** `<-- we are here`

## Similar Projects

* BNFc
* Antlr
* Pkl
* MapStruct
* Nanopass
* [Silver](https://github.com/melt-umn/silver) - Attribute-grammar based language for writing extensible compilers
* [OMeta](https://en.wikipedia.org/wiki/OMeta)
* Ohm
* Meta-II

## What's the name?

Named after [Roc lang](https://github.com/roc-lang/roc) and [Rocq prover](https://rocq-prover.org), but Shamroc is not Roc - it's a sham!
