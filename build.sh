#!/bin/sh

# This script takes a *.py file as an input and creates a *.pyj file as output. 
# The *.pyj file can the be run by JPy.

code="import dis, marshal, sys, types

def show_code(code):
    print('{0}'.format(code.co_argcount))
    print('{0}'.format(code.co_nlocals))
    print('{0}'.format(code.co_stacksize))
    print('{0}'.format(code.co_flags))
    print(' '.join(str(c) for c in list(code.co_code)))
    print('{0}'.format(len(code.co_consts)))
    for const in code.co_consts:
        if type(const) == types.CodeType:
            print('SOC') 
            show_code(const)
        else:
            print('{0!r}'.format(const))
    print('{0}'.format(' '.join(name for name in code.co_names)))
    print('{0}'.format(' '.join(name for name in code.co_varnames)))
    print('{0}'.format(' '.join(name for name in code.co_freevars)))
    print('{0}'.format(' '.join(name for name in code.co_cellvars)))
    print('{0}'.format(code.co_filename))
    print('{0}'.format(code.co_name))
    print('{0}'.format(code.co_kwonlyargcount))

fin = open('$1c', 'rb')
fin.read(12)
code = marshal.load(fin)
show_code(code)"

python3 -m compileall -b "$1"
echo "$code" | python3 | tee "$1"j
rm "$1"c