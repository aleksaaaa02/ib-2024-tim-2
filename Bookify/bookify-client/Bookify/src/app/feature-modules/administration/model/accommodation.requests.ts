export interface AccommodationRequests {
  id: number,
  name: string,
  owner: {
    uid: number,
    firstName: string,
    lastName: string,
    email: string,
    blocked: boolean
  },
  statusRequest: string,
  imageId: number
}
