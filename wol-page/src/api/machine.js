import request from '@/utils/request'

export function getList(params) {
  return request({
    url: '/machine',
    method: 'get',
    params
  })
}

export function getOne(id) {
  return request({
    url: '/machine/' + id,
    method: 'get'
  })
}

export function save(data) {
  return request({
    url: '/machine',
    method: 'post',
    data
  })
}

export function remove(id) {
  return request({
    url: '/machine/' + id,
    method: 'delete'
  })
}

export function modify(data) {
  return request({
    url: '/machine',
    method: 'put',
    data
  })
}

export function wake(id) {
  return request({
    url: '/machine/wake/' + id,
    method: 'get'
  })
}
