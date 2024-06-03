export interface Review {
  id?: number,
  date?: string,
  comment?: string,
  rate?: number,
  accepted?: boolean,
  reported?: boolean,
  guest: {
    uid?:string,
    firstName?:string,
    lastName?:string,
    email?:string,
    imageId?: number
  }
  imageId?: number,
  reviewType: string
}
