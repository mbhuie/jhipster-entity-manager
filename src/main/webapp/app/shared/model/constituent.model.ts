import { IIndex } from 'app/shared/model/index.model';

export interface IConstituent {
  id?: number;
  ric?: string;
  isin?: string;
  sedol?: string;
  currency?: string;
  issueType?: string;
  mic?: string;
  centerCode?: string;
  sharesOutstanding?: number;
  index?: IIndex;
}

export class Constituent implements IConstituent {
  constructor(
    public id?: number,
    public ric?: string,
    public isin?: string,
    public sedol?: string,
    public currency?: string,
    public issueType?: string,
    public mic?: string,
    public centerCode?: string,
    public sharesOutstanding?: number,
    public index?: IIndex
  ) {}
}
