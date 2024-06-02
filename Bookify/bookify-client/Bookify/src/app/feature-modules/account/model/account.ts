import {Address} from "./address";

export interface Account {
  id?: string,
  email?: string,
  firstName?: string,
  lastName?: string,
  blocked?: boolean,
  phone?: string,
  address?: Address,
  imageId?: number
}
