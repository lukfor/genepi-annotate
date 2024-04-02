# genepi-annotate
> Annotate snp list with amino acids.

## Requirements

- min. Java 11 

## Installation

Please download the file `genepi-annotate.jar` from the latest [release](https://github.com/lukfor/genepi-annotate/releases).

## Usage

```
java genepi-annotate.jar annotate
       --input <string> \
       --mutation <string>  \
       --position <string> \
       --output <string> \
       --columnmut <string> \
       --columnwt <string> \
       --reference <string> \
       --maplocus <string>
```

## Parameters

### Input Options

| Option             | Description                                |
|--------------------|--------------------------------------------|
| `--input <string>` | Input csv file                             |
| `--mutation <string>` | Mutation column in input file           |
| `--position <string>` | Position column in input file           |

### Output Options

| Option               | Description                                |
|----------------------|--------------------------------------------|
| `--output <string>`    | Output csv file                            |
| `--columnmut <string>` | New column for wt aas in output file     |
| `--columnwt <string>`  | New column for wildtype aas in output file |

### Reference files

| Option               | Description                                |
|----------------------|--------------------------------------------|
| `--maplocus <string>` | MapLocus filename                          |
| `--reference <string>`| Reference fasta file                       |


## Example

```
java genepi-annotate.jar annotate --input test-input.txt --position POS --mutation MUT --output test-output.txt --columnwt wt_aas --columnmut mut_aas --reference reference.fasta --maplocus maplocus.txt
```

## Contact

This software was developed at the [Institute of Genetic Epidemiology](https://genepi.i-med.ac.at/), [Medical University of Innsbruck](https://i-med.ac.at/)

## License

`genepi-annotate` is MIT Licensed.
