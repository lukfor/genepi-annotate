# genepi-annotate
Annotate snp list with amino acids.

## Usage

```
java genepi-annotate.jar  --columnmut <string> --columnwt <string> --input <string>
       --mutation <string> --offset <int> --output <string> --position
       <string> --reference <string>
```

## Parameters

```
    --columnmut <string>   New column for wt aas in output file
    --columnwt <string>    New column for wildtype aas in output file
    --input <string>       Input csv file
    --mutation <string>    Mutation column in input file
    --offset <int>         Offset
    --output <string>      Output csv file
    --position <string>    Position column in input file
    --reference <string>   Reference fasta file
```    

## Example

```
java genepi-annotate.jar --input test-input.txt --reference reference.fasta --position POS --mutation MUT --offset 2 --output test-output.txt --columnwt wt_aas --columnmut mut_aas
```
