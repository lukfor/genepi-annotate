# genepi-annotate
> Annotate snp list with amino acids.

## Requirements

- min. Java 11 

## Installation

Please download the file `genepi-annotate.jar` from the latest [release](https://github.com/lukfor/genepi-annotate/releases).

## Usage

```
java -jar genepi-annotate.jar annotate --input <string> --mutation <string> --position <string> --output <string> --columnmut <string> --columnwt <string> --reference <string> --maplocus <string>
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

### Reference Files

| Option               | Description                                |
|----------------------|--------------------------------------------|
| `--maplocus <string>` | MapLocus filename                          |
| `--reference <string>`| Reference fasta file                       |


## Example

```
java -jar  genepi-annotate.jar annotate --input test-input.txt --position POS --mutation MUT --output test-output.txt --columnwt wt_aas --columnmut mut_aas --reference reference.fasta --maplocus maplocus.txt
```

## MapLocus File

The MapLocus presents a tabular representation of genomic loci along with pertinent details regarding their positions, characteristics, and coding attributes. It delineates various genomic elements such as introns, exons, splice sites, and their respective positions within a genomic sequence.

In brief, it assigns a freely definable denominator (column `MapLocus`) to portions of the reference sequence, as specified by the position columns.

Also a shorthand denominator and a description of the defined region can be added in the respective columns. The columns `coding` and `translated` define whether our annotation tool should translate the respective sequence sequence.

The column `ReadingFrame` then specifies whether the translation, respectively reading frame starts at base 1 2 or 3 of the respective feature.

An example can be found [here](https://github.com/lukfor/genepi-annotate/blob/master/genepi-annotate/MapLocusLPA%20(FOR%20LONG%20PCR)%20-%20v3.txt).

## Contact

This software was developed at the [Institute of Genetic Epidemiology](https://genepi.i-med.ac.at/), [Medical University of Innsbruck](https://i-med.ac.at/)

## License

`genepi-annotate` is MIT Licensed.
